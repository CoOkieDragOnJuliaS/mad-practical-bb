## Project Structure
 ```text
 src/main/java/at/ac/hcw/procrastinot/
 ├── screen1/         # screen and ViewModel
 ├── data/            # Data layer (Repository and Data Sources)
 │   └── source/
 │       ├── local/   # Room database, DAO, and local entities
 │       └── network/ # Network data source
 ├── di/              # Hilt dependency injection modules
 ├── screen2/         # screen and ViewModel
 ├── screen3/         # screen and ViewModel
 ├── screen4/         # screen and ViewModel
 ├── util/            # Utility classes and shared UI components
 └── ...              # Main Activity, Navigation, and Theme
 ```