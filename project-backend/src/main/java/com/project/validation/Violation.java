package com.project.validation;

public record Violation (String fieldName, String errorMessage){}

// niemutowalna klasa z konstruktorem, oznacza to że te dwa parametry są finalne
// konstruktor uwzględnia te parametry, są gettery, ale nie ma setterów
// raz utworzony nie może być edytowany
// często używany do przenoszenia danych

// używane do przenoszenia błędów o błędach walidacji (zwraca komunikat)
