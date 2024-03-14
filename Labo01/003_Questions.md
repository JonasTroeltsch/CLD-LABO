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
```
TODO
```


* Try to ping the instance ssh srv from your local machine. What do you see?
  Explain. Change the configuration to make it work. Ping the
  instance, record 5 round-trip times.

```
TODO
```

* Determine the IP address seen by the operating system in the EC2
  instance by running the `ifconfig` command. What type of address
  is it? Compare it to the address displayed by the ping command
  earlier. How do you explain that you can successfully communicate
  with the machine?

```
TODO
```
