# TP3

## Fonctionnalités

La solution s'appuie sur un capteur de température et un capteur de lumière connectés à un arduino.

Un diode représente un chauffage de pièce dans le cadre d'une solution de gestion de chauffage dans des locaux d'entreprise.

Un serveur récupère les valeurs des deux capteurs et ordonne l'allumage/éteignage du "chauffage".

Si la la lumière est allumée, on peut déduire que la pière est occupée, le choix d'allulmer ou non le chauffage se fait alors fonction de la température. Si la lumière est éteinte, la pièce est innoccupée, on économise donc du courant en éteignant le chauffage.

## Prérequis

### Le fichier .ino contenant le code exécuté sur le microcontrôleur doit être paramétré

Il faudra :

- Ajouter des identifiants de connection au réseau wifi sur lequel se trouve le broker.

```
//Les id de connexion
const char* ssid = "DESKTOP-ARDPD7O 2351";
const char *password = "36H257v@";
```

- Ajouter l'adresse ip du serveur

```
/*===== MQTT broker/server and TOPICS ========*/
const char* mqtt_server = "192.168.137.41"; /* your ip */
```

### Mosquitto

Afin de démarrer le broker sur une machine linux, il faut executer all.sh, qui contient les instructions nécessaires à l'effacement des anciens messages, le redemarrage du service mosquitto, la publication des topic.

## Serveur java

le serveur java se lance avec maven par la commande

```

mvn exec:java

```

Maven est disponible pour les machines linux dans la plupart des gestionnaires de paquets.
