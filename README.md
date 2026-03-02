# Java Binary Classification Framework

## Overview
This repository contains a custom machine learning library implemented in Java, specifically designed for binary classification. The framework includes a JavaFX graphical user interface and is built from scratch without the use of external machine learning libraries. It demonstrates the practical application of object-oriented design, design patterns, and Java 8 features.

## Features

### Implemented Classifiers
* K-Nearest Neighbors
* Naive Bayes
* Logistic Regression

### Evaluation Metrics
The library includes comprehensive evaluation measures to test algorithm performance:
* Confusion Matrix (True/False Positives, True/False Negatives)
* Precision and Recall

### Data and State Management
* **Data Loading:** Parses and loads datasets from standard CSV files.
* **Configuration:** Hyperparameters and model selection can be configured via the GUI or an external configuration file.

### Graphical User Interface (JavaFX)
The application features a fully interactive UI that allows users to:
* Select input dataset files.
* Choose a classifier and configure its hyperparameters.
* Define train-test split percentages.
* Execute model training.
* View test set results, including the generated confusion matrix.

## Technical Stack
* **Language:** Java 8+
* **GUI:** JavaFX
* **Core Concepts:** OOP, Interfaces, Polymorphism, Generics, Java Collections, Exceptions, I/O Streams, Serialization, Functional Interfaces, Java 8 Streams.

## Setup and Execution
1. Clone the repository.
2. Open the project in an IDE (e.g., IntelliJ IDEA).
3. Ensure JavaFX libraries are configured in the project structure.
4. Run the main application file to launch the GUI.
