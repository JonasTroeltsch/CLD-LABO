* What is the smallest and the biggest instance type (in terms of
  virtual CPUs and memory) that you can choose from when creating an
  instance?

```
The smallest instance type is t2.nano with 1 vCPU and 0.5 GiB of memory.

The biggest instance type is u-6tb1.112xlarge with 448 vCPUs and 6144 GiB of memory.
```

* How long did it take for the new instance to get into the _running_
  state?

```
a few seconds
```

* Using the commands to explore the machine listed earlier, respond to
  the following questions and explain how you came to the answer:

  * What's the difference between time here in Switzerland and the time set on
      the machine?
      
```
$ date
Thu 14 Mar 2024 12:51:13 PM UTC

Time in Switzerland is UTC+1 (at the time of writing 14.03.2024), time on the machine is UTC.
```

  * What's the name of the hypervisor?
```
$ sudo grep -i -e virtual -e vbox -e xen /var/log/kern.log
Mar 14 12:30:10 ip-10-0-9-12 kernel: [    0.076971] Booting paravirtualized kernel on KVM
Mar 14 12:40:53 ip-10-0-14-14 kernel: [    0.101515] Booting paravirtualized kernel on KVM

The hypervisor is KVM.
```

  * How much free space does the disk have?
```bash
~$ df
Filesystem      1K-blocks    Used Available Use% Mounted on
udev               476280       0    476280   0% /dev
tmpfs               97364     384     96980   1% /run
/dev/nvme0n1p1   10090384 3242884   6334364  34% /
tmpfs              486808       0    486808   0% /dev/shm
tmpfs                5120       0      5120   0% /run/lock
/dev/nvme0n1p15    126678   10900    115778   9% /boot/efi
tmpfs               97360       0     97360   0% /run/user/1000
```


* Try to ping the instance ssh srv from your local machine. What do you see?
  Explain. Change the configuration to make it work. Ping the
  instance, record 5 round-trip times.

```ps
PS C:\Users\olinb> ping 15.188.43.46

Pinging 15.188.43.46 with 32 bytes of data:
Request timed out.
Request timed out.
Request timed out.
```

The DMZ server at `15.188.43.46` doesn't return ping requests

* Determine the IP address seen by the operating system in the EC2
  instance by running the `ifconfig` command. What type of address
  is it? Compare it to the address displayed by the ping command
  earlier. How do you explain that you can successfully communicate
  with the machine?

```bash
~$ /sbin/ifconfig
ens5: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 9001
        inet 10.0.14.10  netmask 255.255.255.240  broadcast 10.0.14.15
        inet6 fe80::4c7:80ff:fee1:2eb3  prefixlen 64  scopeid 0x20<link>
        ether 06:c7:80:e1:2e:b3  txqueuelen 1000  (Ethernet)
        RX packets 1577  bytes 158293 (154.5 KiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 1379  bytes 479762 (468.5 KiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
        inet 127.0.0.1  netmask 255.0.0.0
        inet6 ::1  prefixlen 128  scopeid 0x10<host>
        loop  txqueuelen 1000  (Local Loopback)
        RX packets 2061  bytes 7900771 (7.5 MiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 2061  bytes 7900771 (7.5 MiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
```

It sees its ip as `10.0.14.10`. 
