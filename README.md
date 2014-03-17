#[SPR-11557](https://jira.spring.io/browse/SPR-11557)#
#Cannot use @ComponentScan as a meta-annotation#

###Requirements###
1. `git`
2. `maven 3`
3. `java 1.7.0`

###Steps to Reproduce###
1. `git clone https://github.com/sergiofbsilva/spr11557.git`
2. `cd spr11557`
3. `mvn clean package`
4. `cd spr11557-webapp`
5. `mvn tomcat7:run`
