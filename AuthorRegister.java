package edu.ntnu.iir.bidata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Enkelt register for forfattere. Bruker HashMap med forfatter-id som nøkkel
 * for rask oppslag og for å sikre unike id-er.
 */
public class AuthorRegister {
    private final Map<String, Author> authors = new HashMap<>();

    public void addAuthor(Author author) {
        Objects.requireNonNull(author, "forfatter kan ikke være null");
        if (authors.containsKey(author.getId())) {
            throw new IllegalArgumentException("Forfatter med id finnes allerede: " + author.getId());
        }
        authors.put(author.getId(), author);
    }

    public Author findById(String id) {
        Objects.requireNonNull(id, "id kan ikke være null");
        return authors.get(id);
    }

    public List<Author> getAllAuthors() {
        return Collections.unmodifiableList(new ArrayList<>(authors.values()));
    }

    public boolean removeById(String id) {
        Objects.requireNonNull(id, "id must not be null");
        return authors.remove(id) != null;
    }
}
