package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DiaryRegister {
    private final List<DiaryEntry> entries = new ArrayList<>();

    /**
     * Legg til et dagbokinnlegg i registeret.
     * @param entry innlegget (må ikke være null)
     * @throws IllegalArgumentException hvis et innlegg med samme id allerede finnes
     */
    public void addEntry(DiaryEntry entry) {
        Objects.requireNonNull(entry, "innlegg kan ikke være null");
        // Forhindre duplikat-id
        if (entries.stream().anyMatch(e -> e.getId().equals(entry.getId()))) {
            throw new IllegalArgumentException("Innlegg med id finnes allerede: " + entry.getId());
        }
        entries.add(entry);
    }

    /**
     * Finn alle innlegg for en gitt dato (ignorerer tid på døgnet).
     * Returnerer en ny liste med treff i innsettingsrekkefølge.
     */
    public List<DiaryEntry> findByDate(LocalDate date) {
        Objects.requireNonNull(date, "dato kan ikke være null");
        return entries.stream()
                .filter(e -> e.getCreatedAt().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Slett innlegg ved id. Returnerer true hvis noe ble fjernet.
     */
    public boolean deleteById(String id) {
        Objects.requireNonNull(id, "id kan ikke være null");
        return entries.removeIf(e -> e.getId().equals(id));
    }

    /**
     * Returner alle innlegg sortert etter opprettelsestid. Hvis ascending er true,
     * eldste først. Returnerer en ny liste slik at kallende kode ikke kan endre
     * den interne rekkefølgen.
     */
    public List<DiaryEntry> getAllSortedByDate(boolean ascending) {
        List<DiaryEntry> copy = new ArrayList<>(entries);
        Comparator<DiaryEntry> cmp = Comparator.comparing(DiaryEntry::getCreatedAt);
        if (!ascending) cmp = cmp.reversed();
        Collections.sort(copy, cmp);
        return copy;
    }

    /**
     * Return an unmodifiable view of all entries in insertion order.
     */
    public List<DiaryEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Finn alle innlegg med gitt tittel.
     * @param title tittel (må ikke være null)
     * @return liste med treff
     */
    public List<DiaryEntry> findByTitle(String title) {
        Objects.requireNonNull(title, "tittel kan ikke være null");
        return entries.stream()
                .filter(e -> title.equalsIgnoreCase(e.getTitle()))
                .collect(Collectors.toList());
    }

    /**
     * Slett alle innlegg med gitt tittel.
     * @param title tittel (må ikke være null)
     * @return antall slettet
     */
    public int deleteByTitle(String title) {
        Objects.requireNonNull(title, "tittel kan ikke være null");
        int before = entries.size();
        entries.removeIf(e -> title.equalsIgnoreCase(e.getTitle()));
        return before - entries.size();
    }
}
