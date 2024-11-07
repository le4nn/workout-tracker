import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public interface DataAdapter {
    void saveData(String data);

    class FileDataAdapter implements DataAdapter {
        private static final String FILE_NAME = "workouts.txt";

        @Override
        public void saveData(String data) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                writer.write(data);
                writer.newLine();
                System.out.println("Данные сохранены в " + FILE_NAME + ": " + data);
            } catch (IOException e) {
                System.err.println("Ошибка при записи данных в файл: " + e.getMessage());
            }
        }
    }
}
