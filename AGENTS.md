# Agent Context: ProcrastiNot

This file provides a compact overview of the application's structure and technical choices to help AI agents work more efficiently on this project.

## 🚀 Project Overview
- **Name**: ProcrastiNot
- **Purpose**: Task management app demonstrating modern Android development.
- **Main Module**: `:app`
- **Package**: `at.ac.hcw.procrastinot`

## 🛠 Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **DI**: Hilt (`@AndroidEntryPoint`, `@HiltViewModel`, `di` package modules)
- **Architecture**: MVVM with Repository Pattern
- **Navigation**: Jetpack Navigation Compose (`TodoNavGraph.kt`, `TodoNavigation.kt`)
- **Database**: Room (`data/source/local`)
- **Async**: Coroutines & Flow
- **Logging**: Timber

## 🤝 Working Method & Collaboration
- **Team**: Two developers working in parallel.
- **Git Flow**: Work is done in feature branches and then merged/pushed to `main`.
- **Conflict Avoidance**: Tasks are split clearly between developers to minimize merge conflicts.
- **Execution**: Backlog items are processed sequentially, one at a time.

## 🏗 Architecture & Package Structure
The app follows a feature-based package structure combined with a clear separation of concerns in the data layer.

- `data/`: Core data layer
    - `source/`: `local` (Room) and `network` (simulated) data sources.
    - `Task.kt`: The central domain model.
    - `DefaultTasksRepository.kt`: Orchestrates data between local and network sources.
- `tasks/`: Task List screen (Main screen).
- `taskdetail/`: Individual task viewing.
- `addedittask/`: Screen for creating and updating tasks.
- `statistics/`: Statistics overview.
- `di/`: Hilt modules for providing dependencies.
- `ui/`: Theme and common UI utilities.

## 🧭 Navigation
- Managed in `TodoNavGraph.kt`.
- Routes and arguments defined in `TodoNavigation.kt`.
- Uses a `TodoNavigationActions` helper class to encapsulate navigation logic.

## 📝 Common Patterns for Agents
### Adding a New Feature
1. **Model**: Update `Task.kt` or create a new entity in `data/`.
2. **Data**: Add DAO methods in `TasksDao.kt` and update `TasksRepository.kt`.
3. **ViewModel**: Create a new ViewModel in the feature package using `@HiltViewModel`.
4. **UI**: Create a Composable screen and its internal state representation.
5. **Navigation**: Register the new route in `TodoNavigation.kt` and add a `composable` destination in `TodoNavGraph.kt`.

### Data Flow
- **UI -> ViewModel**: User actions call ViewModel methods.
- **ViewModel -> Repository**: ViewModel interacts with `TasksRepository` (usually via `Flow`).
- **Repository -> UI**: Repository exposes `Flow<Result<T>>` or direct data, which the ViewModel converts to UI State using `stateIn`.

## 📋 Implementation Backlog
The following items are planned for implementation:

| ID | Task Name | Description |
|:---|:---|:---|
| **MAD-01** | Implement complete & activate | Toggle tasks between active/completed in list and detail screens. Show Snackbars and persist to DB. |
| **MAD-02** | Task Priorities & Filtering | Assign High/Medium/Low priorities. Display visually and allow filtering on the Tasks screen. |
| **MAD-03** | Fix Statistics Crash | Resolve crash occurring when navigating to statistics with no tasks. |
| **MAD-04** | Seed Database | Populate the database with initial tasks on the first launch. |
| **MAD-05** | Statistics UI Update | Redesign Statistics screen with colored cards (Active/Completed) as per mockup. |
| **MAD-06** | Fix Repeated Snackbar | Ensure "Task added" Snackbar only shows once and doesn't reappear on back navigation. |
| **MAD-07** | Swipe-to-delete | Implement left swipe gesture on Tasks screen to delete tasks with Snackbar confirmation. |

## 🔍 Key Files for Reference
- `MainActivity.kt`: Entry point.
- `TodoNavGraph.kt`: Navigation configuration.
- `data/DefaultTasksRepository.kt`: Primary data coordinator.
- `data/source/local/TasksDao.kt`: Room database operations.
