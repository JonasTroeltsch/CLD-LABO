# Custom AMI and Deploy the second Drupal instance

In this task you will update your AMI with the Drupal settings and deploy it in the second availability zone.

## Task 01 - Create AMI

### [Create AMI](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/ec2/create-image.html)

Note : stop the instance before

|Key|Value for GUI Only|
|:--|:--|
|Name|AMI_DRUPAL_DEVOPSTEAM[XX]_LABO02_RDS|
|Description|Same as name value|

```bash
[INPUT]

aws ec2 create-image --name "AMI_DRUPAL_DEVOPSTEAM14_LABO02_RDS" --description "AMI_DRUPAL_DEVOPSTEAM14_LABO02_RDS" \
--tag-specifications 'ResourceType=image,Tags=[{Key=Name,Value=AMI_DRUPAL_DEVOPSTEAM14_LABO02_RDS}]' \
--instance-id 'i-0a71f8b0c14a977f3'

[OUTPUT]

{
    "ImageId": "ami-04cd5fb0c47a7ce90"
}

```

## Task 02 - Deploy Instances

* Restart Drupal Instance in Az1

* Deploy Drupal Instance based on AMI in Az2

|Key|Value for GUI Only|
|:--|:--|
|Name|EC2_PRIVATE_DRUPAL_DEVOPSTEAM[XX]_B|
|Description|Same as name value|

```ps
aws ec2 run-instances --image-id ami-04cd5fb0c47a7ce90 --instance-type t3.micro --key-name CLD_KEY_DRUPAL_DEVOPSTEAM14 --subnet-id subnet-08532e833f35bd94d --private-ip-address 10.0.14.140 --security-group-ids sg-0021f9c1f6d3ada16 --tag-specifications "ResourceType=instance,Tags=[{Key=Name,Value=EC2_PRIVATE_DRUPAL_DEVOPSTEAM14_B}]" --placement AvailabilityZone=eu-west-3b
{
    "Groups": [],
    "Instances": [
        {
            "AmiLaunchIndex": 0,
            "ImageId": "ami-04cd5fb0c47a7ce90",
            "InstanceId": "i-0aab65b64442b4464",
            "InstanceType": "t3.micro",
            "KeyName": "CLD_KEY_DRUPAL_DEVOPSTEAM14",
            "LaunchTime": "2024-03-21T13:30:44+00:00",
            "Monitoring": {
                "State": "disabled"
            },
            "Placement": {
                "AvailabilityZone": "eu-west-3b",
                "GroupName": "",
                "Tenancy": "default"
            },
            "PrivateDnsName": "ip-10-0-14-140.eu-west-3.compute.internal",
            "PrivateIpAddress": "10.0.14.140",
            "ProductCodes": [],
            "PublicDnsName": "",
            "State": {
                "Code": 0,
                "Name": "pending"
            },
            "StateTransitionReason": "",
            "SubnetId": "subnet-08532e833f35bd94d",
            "VpcId": "vpc-03d46c285a2af77ba",
            "Architecture": "x86_64",
            "BlockDeviceMappings": [],
            "ClientToken": "6216d8cb-de0f-46a2-8a73-4bcea4730041",
            "EbsOptimized": false,
            "EnaSupport": true,
            "Hypervisor": "xen",
            "NetworkInterfaces": [
                {
                    "Attachment": {
                        "AttachTime": "2024-03-21T13:30:44+00:00",
                        "AttachmentId": "eni-attach-00a8ff8e556713455",
                        "DeleteOnTermination": true,
                        "DeviceIndex": 0,
                        "Status": "attaching",
                        "NetworkCardIndex": 0
                    },
                    "Description": "",
                    "Groups": [
                        {
                            "GroupName": "SG-PRIVATE-DRUPAL-DEVOPSTEAM14",
                            "GroupId": "sg-0021f9c1f6d3ada16"
                        }
                    ],
                    "Ipv6Addresses": [],
                    "MacAddress": "0a:4b:8c:ed:1f:87",
                    "NetworkInterfaceId": "eni-00daa69b09e916ca7",
                    "OwnerId": "709024702237",
                    "PrivateIpAddress": "10.0.14.140",
                    "PrivateIpAddresses": [
                        {
                            "Primary": true,
                            "PrivateIpAddress": "10.0.14.140"
                        }
                    ],
                    "SourceDestCheck": true,
                    "Status": "in-use",
                    "SubnetId": "subnet-08532e833f35bd94d",
                    "VpcId": "vpc-03d46c285a2af77ba",
                    "InterfaceType": "interface"
                }
            ],
            "RootDeviceName": "/dev/xvda",
            "RootDeviceType": "ebs",
            "SecurityGroups": [
                {
                    "GroupName": "SG-PRIVATE-DRUPAL-DEVOPSTEAM14",
                    "GroupId": "sg-0021f9c1f6d3ada16"
                }
            ],
            "SourceDestCheck": true,
            "StateReason": {
                "Code": "pending",
                "Message": "pending"
            },
            "Tags": [
                {
                    "Key": "Name",
                    "Value": "EC2_PRIVATE_DRUPAL_DEVOPSTEAM14_B"
                }
            ],
            "VirtualizationType": "hvm",
            "CpuOptions": {
                "CoreCount": 1,
                "ThreadsPerCore": 2
            },
            "CapacityReservationSpecification": {
                "CapacityReservationPreference": "open"
            },
            "MetadataOptions": {
                "State": "pending",
                "HttpTokens": "optional",
                "HttpPutResponseHopLimit": 1,
                "HttpEndpoint": "enabled",
                "HttpProtocolIpv6": "disabled",
                "InstanceMetadataTags": "disabled"
            },
            "EnclaveOptions": {
                "Enabled": false
            },
            "PrivateDnsNameOptions": {
                "HostnameType": "ip-name",
                "EnableResourceNameDnsARecord": false,
                "EnableResourceNameDnsAAAARecord": false
            },
            "MaintenanceOptions": {
                "AutoRecovery": "default"
            },
            "CurrentInstanceBootMode": "legacy-bios"
        }
    ],
    "OwnerId": "709024702237",
    "ReservationId": "r-0f9c14fe8d1e7256f"
}
```

## Task 03 - Test the connectivity

### Update your ssh connection string to test

* add tunnels for ssh and http pointing on the B Instance

```bash
ssh devopsteam14@15.188.43.46 -i CLD_KEY_DMZ_DEVOPSTEAM14.pem -L 2224:10.0.14.140:22
ssh devopsteam14@15.188.43.46 -i CLD_KEY_DMZ_DEVOPSTEAM14.pem -L 2223:10.0.14.10:22
```

## Check SQL Accesses

```sql
bitnami@ip-10-0-14-10:~$ mariadb -h dbi-devopsteam14.cshki92s4w5p.eu-west-3.rds.amazonaws.com -u bn_drupal -p
Enter password:
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 239
Server version: 10.11.7-MariaDB managed by https://aws.amazon.com/rds/

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [(none)]>
```

```sql
bitnami@ip-10-0-14-140:~$ mariadb -h dbi-devopsteam14.cshki92s4w5p.eu-west-3.rds.amazonaws.com -u bn_drupal -p
Enter password:
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 252
Server version: 10.11.7-MariaDB managed by https://aws.amazon.com/rds/

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [(none)]>
```

### Check HTTP Accesses

```bash
ssh devopsteam14@15.188.43.46 -i CLD_KEY_DMZ_DEVOPSTEAM14.pem -L 2224:10.0.14.140:22 -L 888:10.0.14.140:8080
ssh devopsteam14@15.188.43.46 -i CLD_KEY_DMZ_DEVOPSTEAM14.pem -L 2223:10.0.14.10:22 -L 887:10.0.14.140:8080
```

### Read and write test through the web app

* Login in both webapps (same login)

* Change the users' email address on a webapp... refresh the user's profile page on the second and validated that they are communicating with the same db (rds).

* Observations ?

It changes on both pages
### Change the profil picture

* Observations ?

It changes on both pages, but the second can't load the file