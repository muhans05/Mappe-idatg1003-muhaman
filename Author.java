package edu.ntnu.iir.bidata;

import java.util.Objects;

/**
 * Enkel klasse som representerer en forfatter av dagbokinnlegg.
 */
public class Author {
    private final String id;
    private String name;

    public Author(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id kan ikke være null eller tom");
        }
        Objects.requireNonNull(name, "navn kan ikke være null");
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "navn kan ikke være null");
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return id.equals(author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Author{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
