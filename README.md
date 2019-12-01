# MazeSolvingAlgorithmsComparison
Podstawy Sztucznej Inteligencji [PSZT]<br/>
Projekt 1 - Labirynt<br/>

<b>Authors</b><br/>
- Paweł Ś. - paolo21d
- Grzegorz A. - Quazan


<b>Opis:</b><br>
Napisać program porównujący działanie algorytmów przeszukiwania BFS, DFS, IDFS dla problemu znalezienia drogi w labiryncie.<br/>
Przestrzeń dyskretna - dozwolone ruchy to góra, dół, lewo, prawo.<br/>
WE: plik ze strukturą labiryntu z we/wy (dla większych użyć jakiegoś generatora).<br/> 
WY: znaleziona najkrótsza ścieżka i/lub mapka z zaznaczoną ścieżką.<br/>
Więcej info na http://home.elka.pw.edu.pl/~mmarkiew/2019-pszt.html

<b>KanBan Board in Trello</b><br>
https://trello.com/b/LbkAuX9o/pszt-p1-labirynt

<b>Podstawowe problemy:<b>
1) Generowanie labiryntu
    - reprezentacja labiryntu w pamięci komputera (format, struktury danych)
    - wczytanie laboryntu z pliku lub prosto od użytkownika (ustalenie formatu danych)
    - wydruk labiryntu na ekran
    - zaznaczenie na obrazie reprezentującym labirynt optymalnej drogi przejścia
2) Implementacja algorytmów rozwiązywania labiryntu
    - BFS
    - DFS
    - IDFS
3) Procedura porównywania wydajności algorytmów 
    - 'rozgrzanie' maszyny wirtualnej Javy
    - uruchomienie kolejnych algorytmów z pomiarem czasu
    - wygenerowanie statystyk dla algorytmów
    
<b>Budowanie projektu:<b>
- do budowania został użyty maven
- mvn clean install

<b>Uruchomienie aplikacji:<b><br/>
- po zbudowaniu projektu należy przejść do folderu target
- uruchomiamy za pomocą komendy: java - jar MazeSolver-1.0.jar