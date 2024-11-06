public interface Observer {
    void update(String message);

    interface Subject {
        void registerObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers(String message);
    }

    class StatsModule implements Observer {
        private ProgressTracker progressTracker;

        public StatsModule(ProgressTracker progressTracker) {
            this.progressTracker = progressTracker;
        }

        @Override
        public void update(String message) {
            System.out.println("Обновление: " + message);
            System.out.println(progressTracker.getProgressSummary());
        }
    }
}
