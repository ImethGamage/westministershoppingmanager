# Westminster Shopping Manager

A Java-based desktop Shopping Management System developed using Object-Oriented Programming (OOP) principles.

## Overview

Westminster Shopping Manager is a desktop application developed in Java for managing products and providing a simple shopping experience.

The system supports different types of products, including Electronics and Clothing, and provides both product management functionality and a graphical user interface for browsing products and managing a shopping cart.

The project demonstrates the practical application of core Object-Oriented Programming concepts in Java.

## Technologies

- Java
- Java Swing
- Object-Oriented Programming (OOP)
- File Handling

## OOP Concepts Used

- Classes and Objects
- Encapsulation
- Inheritance
- Abstraction
- Polymorphism
- Interfaces

The system uses an abstract `Product` class as the base class for product types such as `Electronics` and `Clothing`.

A `ShoppingManager` interface defines the main product-management operations.

## Main Features

- Add new products
- Delete products
- Display the list of available products
- Support Electronics and Clothing product categories
- Save and load product information using a text file
- Filter products by category
- Display detailed product information
- Add products to a shopping cart
- Calculate the total shopping cart cost
- Apply a 20% discount when three or more products from the same category are purchased
- Graphical User Interface developed using Java Swing
- Sort and display products through a table-based interface

## Product Types

### Electronics
Stores information such as:
- Product ID
- Product name
- Number of available items
- Price
- Brand
- Warranty period

### Clothing
Stores information such as:
- Product ID
- Product name
- Number of available items
- Price
- Size
- Colour

## Project Structure

The main classes include:

- `Product` – Abstract base class for products
- `Electronics` – Represents electronic products
- `Clothing` – Represents clothing products
- `ShoppingManager` – Interface defining product-management operations
- `westminsterShoppingManager` – Implements the shopping-management functionality
- `ShoppingCart` – Handles products added to the cart and price calculations
- `GUI` – Provides the Java Swing graphical user interface
- `User` – Represents user information
- `Main` – Application entry point
