package com.mycompany.GutenbergBookExplorer;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;

public class GutenbergBookExplorer {

    static Scanner input = new Scanner(System.in);

    // Fixed-size arrays (no ArrayList)
    static Book[] books = new Book[1000];
    static Book[] readingList = new Book[20];

    static int bookCount = 0;
    static int readingCount = 0;

    static boolean catalogLoaded = false;

    // Book class
    static class Book {
        String title;
        String author;
        String price;
        String availability;
        String rating;
        String url;

        Book(String title, String author, String price,
             String availability, String rating, String url) {

            this.title = title;
            this.author = author;
            this.price = price;
            this.availability = availability;
            this.rating = rating;
            this.url = url;
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("Loading Books to Scrape catalog...");

        loadCatalog();

        int choice;

        do {
            System.out.println("\n===== GUTENBERG BOOK EXPLORER =====");
            System.out.println("1. Explore Books");
            System.out.println("2. Search by Title");
            System.out.println("3. Search by Author");
            System.out.println("4. Add Book to Reading List");
            System.out.println("5. View Reading List");
            System.out.println("6. Remove Book");
            System.out.println("7. Display Book Details");
            System.out.println("0. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    exploreBooks();
                    break;

                case 2:
                    System.out.print("Enter book title: ");
                    searchBook(input.nextLine());
                    break;

                case 3:
                    System.out.print("Enter author name: ");
                    searchBook(input.nextLine(), true);
                    break;

                case 4:
                    addBook();
                    break;

                case 5:
                    viewReadingList();
                    break;

                case 6:
                    removeBook();
                    break;

                case 7:
                    displayBookDetails();
                    break;

                case 0:
                    System.out.println("Thank you for using the program!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        input.close();
    }

    // READ AND VALIDATE INTEGER INPUT
    static int readInt(String message) {

        while (true) {
            System.out.print(message);

            String value = input.nextLine();

            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // CONNECT TO WEBSITE AND LOAD BOOKS
    static void loadCatalog() {

        String nextPage = "https://books.toscrape.com/";

        int pageCount = 0;

        try {

            while (nextPage != null
                    && bookCount < books.length
                    && pageCount < 50) {

                Document doc = Jsoup.connect(nextPage)
                        .userAgent("Mozilla/5.0")
                        .timeout(15000)
                        .get();

                Elements bookElements =
                        doc.select("article.product_pod");

                for (Element element : bookElements) {

                    String title =
                            element.select("h3 a").attr("title");

                    String price =
                            element.select(".price_color").text();

                    String availability =
                            element.select(".availability").text().trim();

                    String rating =
                            element.select(".star-rating")
                            .attr("class")
                            .replace("star-rating", "").trim();

                    String url =
                            element.selectFirst("h3 a").absUrl("href");

                    Book newBook = new Book(
                            title,
                            "Not provided by website",
                            price,
                            availability,
                            rating,
                            url
                    );

                    if (!containsBook(url)) {
                        books[bookCount] = newBook;
                        bookCount++;
                    }
                }

                Element next =
                        doc.selectFirst("li.next a");

                if (next != null) {
                    nextPage = next.absUrl("href");
                } else {
                    nextPage = null;
                }

                pageCount++;
            }

            catalogLoaded = true;

            System.out.println("Catalog loaded successfully!");
            System.out.println("Books retrieved: " + bookCount);

        } catch (Exception e) {

            catalogLoaded = bookCount > 0;

            System.out.println("Connection error: " + e.getMessage());
            System.out.println("Books retrieved so far: " + bookCount);
        }
    }

    // CHECK FOR DUPLICATE BOOKS
    static boolean containsBook(String url) {

        for (int i = 0; i < bookCount; i++) {

            if (books[i].url.equals(url)) {
                return true;
            }
        }

        return false;
    }

    // OPTION 1: EXPLORE BOOKS
    static void exploreBooks() {

        if (!catalogLoaded || bookCount == 0) {
            System.out.println("No books are currently available.");
            return;
        }

        System.out.println("\n===== AVAILABLE BOOKS =====");

        int limit = Math.min(bookCount, 10);

        for (int i = 0; i < limit; i++) {

            System.out.println("\nBook Number: " + (i + 1));
            System.out.println("Title: " + books[i].title);
            System.out.println("Price: " + books[i].price);
            System.out.println("Availability: " + books[i].availability);
            System.out.println("Rating: " + books[i].rating);
            System.out.println("---------------------------");
        }

        System.out.println("Showing " + limit + " books.");
    }

    // OPTION 2: SEARCH BY TITLE
    // OVERLOADED METHOD 1
    static void searchBook(String title) {

        boolean found = false;

        System.out.println("\n===== TITLE SEARCH RESULTS =====");

        for (int i = 0; i < bookCount; i++) {

            if (books[i].title.toLowerCase()
                    .contains(title.toLowerCase())) {

                System.out.println("\nBook Number: " + (i + 1));
                System.out.println("Title: " + books[i].title);
                System.out.println("Price: " + books[i].price);
                System.out.println("URL: " + books[i].url);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching books found.");
        }
    }

    // OPTION 3: SEARCH BY AUTHOR
    // OVERLOADED METHOD 2
    static void searchBook(String keyword,
                           boolean searchByAuthor) {

        if (searchByAuthor) {

            System.out.println("\nAuthor search is unavailable.");
            System.out.println(
                "Books to Scrape does not provide author names."
            );

        } else {

            searchBook(keyword);
        }
    }

    // OPTION 4: ADD BOOK TO READING LIST
    static void addBook() {

        if (bookCount == 0) {
            System.out.println("No books available to add.");
            return;
        }

        if (readingCount == readingList.length) {
            System.out.println("Your reading list is full!");
            return;
        }

        exploreBooks();

        int number = readInt(
                "\nEnter the book number you want to add: ");

        if (number < 1 || number > Math.min(bookCount, 10)) {
            System.out.println("Invalid book number.");
            return;
        }

        Book selected = books[number - 1];

        for (int i = 0; i < readingCount; i++) {

            if (readingList[i].url.equals(selected.url)) {
                System.out.println("This book is already in your list.");
                return;
            }
        }

        readingList[readingCount] = selected;
        readingCount++;

        System.out.println("Book added successfully!");
    }

    // OPTION 5: VIEW READING LIST
    static void viewReadingList() {

        System.out.println("\n===== MY READING LIST =====");

        if (readingCount == 0) {
            System.out.println("Your reading list is empty.");
            return;
        }

        for (int i = 0; i < readingCount; i++) {

            System.out.println("\nBook Number: " + (i + 1));
            System.out.println("Title: " + readingList[i].title);
            System.out.println("Price: " + readingList[i].price);
            System.out.println("URL: " + readingList[i].url);
            System.out.println("---------------------------");
        }
    }

    // OPTION 6: REMOVE BOOK
    static void removeBook() {

        if (readingCount == 0) {
            System.out.println("Your reading list is empty.");
            return;
        }

        viewReadingList();

        int number = readInt(
                "\nEnter the book number to remove: ");

        if (number < 1 || number > readingCount) {
            System.out.println("Invalid book number.");
            return;
        }

        int index = number - 1;

        for (int i = index; i < readingCount - 1; i++) {

            readingList[i] = readingList[i + 1];
        }

        readingList[readingCount - 1] = null;
        readingCount--;

        System.out.println("Book removed successfully!");
    }

    // OPTION 7: DISPLAY BOOK DETAILS
    static void displayBookDetails() {

        if (bookCount == 0) {
            System.out.println("No books available.");
            return;
        }

        exploreBooks();

        int number = readInt(
                "\nEnter the book number: ");

        if (number < 1 || number > Math.min(bookCount, 10)) {
            System.out.println("Invalid book number.");
            return;
        }

        Book selected = books[number - 1];

        System.out.println("\n===== BOOK DETAILS =====");
        System.out.println("Title: " + selected.title);
        System.out.println("Author: " + selected.author);
        System.out.println("Price: " + selected.price);
        System.out.println("Availability: " + selected.availability);
        System.out.println("Rating: " + selected.rating);
        System.out.println("Book URL: " + selected.url);
    }
}