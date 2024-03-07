## Commands to run 

### Subnet

```bash
aws ec2 create-subnet \
--vpc-id vpc-03d46c285a2af77ba \
--cidr-block 10.0.14.0/28 \
--availability-zone-id euw3-az1 \
--tag-specifications 'ResourceType=subnet,Tags=[{Key=Name,Value=SUB-PRIVATE-DEVOPSTEAM14}]'
```

