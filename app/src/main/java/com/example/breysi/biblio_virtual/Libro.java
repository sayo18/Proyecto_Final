package com.example.breysi.biblio_virtual;

/**
 * Created by breysi on 23/03/2018.
 */

class Libro {

private String titulo;
private String autor;
private String anyEdicion;
private String editorial;
private String genero;
private String idioma;
private String npaginas;
private String portada;

    public Libro(String titulo, String autor, String anyEdicion, String editorial, String genero, String idioma, String npaginas, String portada) {
        this.titulo = titulo;
        this.autor = autor;
        this.anyEdicion = anyEdicion;
        this.editorial = editorial;
        this.genero = genero;
        this.idioma = idioma;
        this.npaginas = npaginas;
        this.portada = portada;
    }

    public Libro(){

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAnyEdicion() {
        return anyEdicion;
    }

    public void setAnyEdicion(String anyEdicion) {
        this.anyEdicion = anyEdicion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNpaginas() {
        return npaginas;
    }

    public void setNpaginas(String npaginas) {
        this.npaginas = npaginas;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
