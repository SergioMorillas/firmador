#!/bin/bash

#openssl pkcs12 -export -out prueba.p12 -inkey private-key.pem -in cert.pem -passout pass:prueba123

for i in {1..10}
do
	sh -c  "openssl pkcs12 -export -out fich${i}.p12 -inkey /home/sergio/firmador/firmador/${i}/private-key.pem -in /home/sergio/firmador/firmador/${i}/cert.pem -passout pass:prueba123"
done
