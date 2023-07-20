import java.time.LocalDate;

class Task {
    String title, description, status, category;
    LocalDate date;
    public Task(String title, String description, LocalDate date, String status, String category){
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
        this.category = category;
    }
}
