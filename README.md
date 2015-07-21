# Exemplo de automação para Android em Wereables com Appium

Este repositório consiste em duas partes:

  - Projeto no Android Studio com uma app de notificação (dispositivo) e uma app de exemplo wereable
  - Projeto no Eclipse IDE com Appium com o script de automação

## Projetos

### Android Studio
Consiste em dois módulos, um app que possui um botão para emitir uma notificação para o wearable, e o módulo mobile que possui uma única tela (estilo hello world).

### Eclipse IDE
Possui o script de automação com o Appium para emitir a notificação via app e recebê-la nativamente no mobile (wear)

## Pré Requisitos
1. Iniciar um emulador para o dispositvo e outro para o wearable (o emulador do device precisa ser uma imagem do googleapis)
2. Fazer o rota para conexão entre emulador e dispositivo 
```sh
adb -d forward tcp:5601 tcp:5601
```
ou
```sh
telnet localhost <porta>
redir add tcp:5601:5601
```
3. Iniciar o Appium para cada dispositivo
```sh
appium –p 4723 –-udid <dispositivo> &
appium –p 4724 –-udid <wear>  
```
* dispositivo: nome do dispositivo via adb devices
* wear: nome do dispositivo wear via adb devices

## Execução
Executar o script de teste do contido no projeto do Eclipse IDE.

