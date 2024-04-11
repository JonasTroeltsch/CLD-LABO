# Task 002 - Configure Auto Scaling

![Schema](./img/CLD_AWS_INFA.PNG)

* Follow the instructions in the tutorial [Getting started with Amazon EC2 Auto Scaling](https://docs.aws.amazon.com/autoscaling/ec2/userguide/GettingStartedTutorial.html) to create a launch template.

* [CLI Documentation](https://docs.aws.amazon.com/cli/latest/reference/autoscaling/)

## Pre-requisites

* Networks (RTE-TABLE/SECURITY GROUP) set as at the end of the Labo2.
* 1 AMI of your Drupal instance
* 0 existing ec2 (even is in stopped state)
* 1 RDS Database instance - started
* 1 Elastic Load Balancer - started

## Create a new launch configuration. 

|Key|Value|
|:--|:--|
|Name|LT-DEVOPSTEAM[XX]|
|Version|v1.0.0|
|Tag|Name->same as template's name|
|AMI|Your Drupal AMI|
|Instance type|t3.micro (as usual)|
|Subnet|Your subnet A|
|Security groups|Your Drupal Security Group|
|IP Address assignation|Do not assign|
|Storage|Only 10 Go Storage (based on your AMI)|
|Advanced Details/EC2 Detailed Cloud Watch|enable|
|Purchase option/Request Spot instance|disable|

```
[INPUT]
aws ec2 create-launch-template `
    --launch-template-name LT-DEVOPSTEAM14 `
    --version-description v1.0.0 `
    --tag-specifications 'ResourceType=launch-template,Tags=[{Key=Name,Value=LT-DEVOPSTEAM14}]' `
    --launch-template-data '{
        \"ImageId\": \"ami-04cd5fb0c47a7ce90\",
        \"InstanceType\": \"t3.micro\",
        \"NetworkInterfaces\": [
            {
                \"AssociatePublicIpAddress\": false,
                \"DeviceIndex\": 0,
                \"SubnetId\": \"subnet-03f814992c543a1f8\",
                \"Groups\": [\"sg-0021f9c1f6d3ada16\"]
            }
        ],
        \"BlockDeviceMappings\": [
            {
                \"DeviceName\": \"/dev/xvda\",
                \"Ebs\": {
                    \"VolumeSize\": 10,
                    \"VolumeType\": \"gp3\"
                }
            }
        ],
        \"Monitoring\": {
            \"Enabled\": true
        }
    }'

[OUTPUT]
{
    "LaunchTemplate": {
        "LaunchTemplateId": "lt-07b8266627fc6f417",
        "LaunchTemplateName": "LT-DEVOPSTEAM14",
        "CreateTime": "2024-04-11T13:41:30+00:00",
        "CreatedBy": "arn:aws:iam::709024702237:user/CLD_DEVOPSTEAM14",
        "DefaultVersionNumber": 1,
        "LatestVersionNumber": 1,
        "Tags": [
            {
                "Key": "Name",
                "Value": "LT-DEVOPSTEAM14"
            }
        ]
    }
}
```

## Create an auto scaling group

* Choose launch template or configuration

|Specifications|Key|Value|
|:--|:--|:--|
|Launch Configuration|Name|ASGRP_DEVOPSTEAM[XX]|
||Launch configuration|Your launch configuration|
|Instance launch option|VPC|Refer to infra schema|
||AZ and subnet|AZs and subnets a + b|
|Advanced options|Attach to an existing LB|Your ELB|
||Target group|Your target group|
|Health check|Load balancing health check|Turn on|
||health check grace period|10 seconds|
|Additional settings|Group metrics collection within Cloud Watch|Enable|
||Health check grace period|10 seconds|
|Group size and scaling option|Desired capacity|1|
||Min desired capacity|1|
||Max desired capacity|4|
||Policies|Target tracking scaling policy|
||Target tracking scaling policy Name|TTP_DEVOPSTEAM[XX]|
||Metric type|Average CPU utilization|
||Target value|50|
||Instance warmup|30 seconds|
||Instance maintenance policy|None|
||Instance scale-in protection|None|
||Notification|None|
|Add tag to instance|Name|AUTO_EC2_PRIVATE_DRUPAL_DEVOPSTEAM[XX]|

```
[INPUT]
//cli command

[OUTPUT]
```

* Result expected

The first instance is launched automatically.

Test ssh and web access.

```
[INPUT]
//ssh login

[OUTPUT]
```

```
//screen shot, web access (login)
```