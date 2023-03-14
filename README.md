# Programowanie zwinne

## Lab 2
APLIKACJA DO ZARZĄDZANIA PROJEKTAMI 

## Front-End
### Zrobione
1. Skopiowanie pakietu model z poprzednich podpunktów oraz odpowiednia modufikacja
2. Przekopiowanie pakietu service oraz dodanie klas ProjektServiceImpl, ZadanieServiceImpl, StudentServiceImpl, RestResponsePage oraz ServiceUtil
3. Dodanie pakietu config oraz klasy Security Config
4. Utworzony plik tekstowy application.properties w katalogu resources
5. Dodany ProjectController
6. Dodany przykładowy szablon ekranu edycji projektu

### TODO
1. Zaimplementuj pozostałe klasy – ZadanieServiceImpl i StudentServiceImpl.

## Back-End
### Zrobione:
1. Utworzyć serwisy dla Zadania i Studenta (podpunkt 3.4)
2. Dodać controller ZadanieRestController, StudentRestController na bazie ProjektRestController (podpunkt 3.5)
    - Pliki w których trzeba odwzorować kod na podstawie ProjektService, ProjektServiceImpl, ProjektRestController. (:
    
     ![todo](https://i.imgur.com/hiB7hws.png)
3. Naprawić błąd - Cannot invoke "com.project.model.Projekt.getProjektId()" because "createdProjekt" is null4
4. Testy (podpunkt 4)
    
### Podsumowanie:
|  |  **Jagoda** |  **Monika** |  **Błażej** | **Maciej** |
| --- | --- | --- | --- | --- |
|  **Kto co zrealizował?** | backend | testowanie | testowanie | frontend |
|  **Co zamierza zrealizować?** | ... | ... | ... | ... |
|  **Co się nie udało?** | ... | ... | ... | ... |
