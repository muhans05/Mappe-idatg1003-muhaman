package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class UI {
    private DiaryRegister diaryRegister;
    private AuthorRegister authorRegister;
    private final Scanner scanner = new Scanner(System.in);

    public void init() {
        // Initialize registers
    this.diaryRegister = new DiaryRegister();
    this.authorRegister = new AuthorRegister();
    System.out.println("UI initialisert");
    }

    public void start() {
    System.out.println("Velkommen til DagbokApp (interaktiv modus)");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listEntries();
                case "2" -> addAuthorInteractive();
                case "3" -> addEntryInteractive();
                case "4" -> findByDateInteractive();
                    case "5" -> findByTitleInteractive();
                    case "6" -> deleteByTitleInteractive();
                case "7" -> listAuthors();
                case "0" -> {
                    running = false;
                    System.out.println("Hadebra.");
                }
                default -> System.out.println("Ukjent, vær så snill og prøv igjen.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
    System.out.println("--- Dagbokmeny ---");
    System.out.println("1) Vis innlegg (nyeste først)");
    System.out.println("2) Legg til forfatter");
    System.out.println("3) Legg til innlegg");
    System.out.println("4) Finn innlegg på dato");
    System.out.println("5) Finn innlegg på tittel");
    System.out.println("6) Slett innlegg ved tittel");
    System.out.println("7) Vis forfattere");
    System.out.println("0) Avslutt");
    System.out.print("Velg: ");
    }

    private void listEntries() {
        List<DiaryEntry> all = diaryRegister.getAllSortedByDate(false);
        if (all.isEmpty()) {
            System.out.println("Ingen innlegg er registrert.");
            return;
        }
        for (DiaryEntry e : all) {
            printEntry(e);
        }
    }

    private void listAuthors() {
        List<Author> authors = authorRegister.getAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("Ingen forfattere er registrert.");
            return;
        }
        System.out.println("Forfattere:");
        for (Author a : authors) {
            System.out.println("  id=" + a.getId() + ", navn=" + a.getName());
        }
    }

    private void addAuthorInteractive() {
        System.out.print("Forfatter-id: ");
        String id = scanner.nextLine().trim();
        System.out.print("Forfatter-navn: ");
        String name = scanner.nextLine().trim();
        try {
            Author a = new Author(id, name);
            authorRegister.addAuthor(a);
            System.out.println("Forfatter lagt til: " + a);
        } catch (Exception ex) {
            System.out.println("Kunne ikke legge til forfatter: " + ex.getMessage());
        }
    }

    private void addEntryInteractive() {
        if (authorRegister.getAllAuthors().isEmpty()) {
            System.out.println("Ingen forfattere. Legg til forfatter først.");
            return;
        }
        System.out.println("Velg forfatter-id fra listen:");
        listAuthors();
        System.out.print("Forfatter-id: ");
        String authorId = scanner.nextLine().trim();
        Author author = authorRegister.findById(authorId);
        if (author == null) {
            System.out.println("Ukjent forfatter-id: " + authorId);
            return;
        }
        System.out.print("Tittel: ");
        String title = scanner.nextLine();
        System.out.println("Innhold (en linje): ");
        String content = scanner.nextLine();
        try {
            DiaryEntry e = new DiaryEntry(author, title, content);
            diaryRegister.addEntry(e);
            System.out.println("Innlegg lagt til med id: " + e.getId());
        } catch (Exception ex) {
            System.out.println("Kunne ikke legge til innlegg: " + ex.getMessage());
        }
    }

    private void findByDateInteractive() {
        System.out.print("Skriv dato (ÅÅÅÅ-MM-DD): ");
        String input = scanner.nextLine().trim();
        try {
            LocalDate d = LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
            List<DiaryEntry> results = diaryRegister.findByDate(d);
            if (results.isEmpty()) {
                System.out.println("Ingen innlegg for " + d);
            } else {
                for (DiaryEntry e : results) printEntry(e);
            }
        } catch (DateTimeParseException ex) {
            System.out.println("Ugyldig datoformat");
        }
    }

    private void findByTitleInteractive() {
        System.out.print("Skriv tittel for å finne innlegg: ");
        String tittel = scanner.nextLine().trim();
        List<DiaryEntry> treff = diaryRegister.findByTitle(tittel);
        if (treff.isEmpty()) {
            System.out.println("Ingen innlegg med tittel: " + tittel);
        } else {
            for (DiaryEntry e : treff) printEntry(e);
        }
    }

    private void deleteByTitleInteractive() {
        System.out.print("Skriv tittel for å slette innlegg: ");
        String tittel = scanner.nextLine().trim();
        int antall = diaryRegister.deleteByTitle(tittel);
        System.out.println(antall > 0 ? ("Slettet " + antall + " innlegg med tittel: " + tittel) : "Fant ingen innlegg med tittel: " + tittel);
    }

    private void printEntry(DiaryEntry entry) {
        System.out.println(entry);
        System.out.println("  ID: " + entry.getId());
        System.out.println("  Forfatter: " + (entry.getAuthor() == null ? "" : entry.getAuthor().getName()));
        System.out.println("  Tittel: " + entry.getTitle());
        System.out.println("  Innhold: " + entry.getContent());
        System.out.println("  Tid: " + entry.getCreatedAt());
        System.out.println();
    }
}
