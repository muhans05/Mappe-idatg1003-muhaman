package edu.ntnu.iir.bidata; // Pakkenavnet der klassen ligger

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Klasse som representerer et dagboksinnlegg.
 * Holder på data og sørger for enkel validering.
 * <p>
 * bytte med setters for ryddigere og bedre eller lik resultat
 *
 */
public class DiaryEntry {
    private final String id; // Unik ID (kan ikke endres)
    private Author author;   // Forfatter (kan endres)
    private String title;    // Tittel (kan endres)
    private String content;  // Innhold (kan endres)
    private final LocalDateTime createdAt; // Tidspunkt for opprettelse (kan ikke endres)

    /**
     * Konstruktør som lager et nytt dagboksinnlegg.
     */
    public DiaryEntry(String id, Author author, String title, String content, LocalDateTime createdAt) {
        // Sjekker at id ikke er null eller tom
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID kan ikke være null eller tom");
        }
        // Sjekker at forfatter ikke er null
        Objects.requireNonNull(author, "Forfatter kan ikke være null");
        // Sjekker at tittel og innhold ikke er null
        Objects.requireNonNull(title, "Tittel kan ikke være null");
        Objects.requireNonNull(content, "Innnhold kan ikke være null");

        // Setter feltverdier
        this.id = id;
    this.author = author;
        this.title = title;
        this.content = content;
        // Hvis createdAt er null, settes den til nåværende tid
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    /**
     * Hjelpekonstruktør som genererer en unik id og setter opprettelsestid til nå.
     * @param author forfatter (må ikke være null)
     * @param title tittel (må ikke være null)
     * @param content innhold (må ikke være null)
     */
    public DiaryEntry(Author author, String title, String content) {
        this(UUID.randomUUID().toString(), author, title, content, LocalDateTime.now());
    }

    // Getter for id (kan ikke endres)
    public String getId() {
        return id;
    }

    // Getter for forfatter
    public Author getAuthor() {
        return author;
    }

    /**
     * Setter ny forfatter (må ikke være null)
     */
    public void setAuthor(Author author) {
        Objects.requireNonNull(author, "Forfatter må ikke være null");
        this.author = author;
    }

    // Getter for tittel
    public String getTitle() {
        return title;
    }

    /**
     * Setter ny tittel (kan være tom, men ikke null)
     */
    public void setTitle(String title) {
        Objects.requireNonNull(title, "Tittle kan ikke være null");
        this.title = title;
    }

    // Getter for innhold
    public String getContent() {
        return content;
    }

    /**
     * Setter nytt innhold (kan være tomt, men ikke null)
     */
    public void setContent(String content) {
        Objects.requireNonNull(content, "Innhold kan ikkke være null");
        this.content = content;
    }

    // Getter for opprettelsestidspunkt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // toString gir en enkel tekstrepresentasjon av objektet
    @Override
    public String toString() {
    return "DiaryEntry{" +
        "id='" + id + '\'' +
        ", author='" + (author == null ? "" : author.getName()) + '\'' +
        ", title='" + title + '\'' +
        ", createdAt=" + createdAt +
        '}';
    }

    // equals: to objekter er like hvis de har samme id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Samme objekt
        if (!(o instanceof DiaryEntry that)) return false; // Ikke samme type
        return id.equals(that.id); // Samme id = lik
    }

    // hashCode basert på id
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
