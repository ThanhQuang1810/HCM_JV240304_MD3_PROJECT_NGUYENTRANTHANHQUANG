import models.Category;
import models.Order;
import models.Product;
import models.User;
import services.CategoryService;
import services.OrderService;
import services.ProductService;
import services.UserService;
import utils.IOFile;
import utils.InputUtil;

import java.io.*;
import java.util.*;

import static utils.IOFile.PRODUCT_PATH;

public class Main {
    private static CategoryService categoryService = new CategoryService();
    private static ProductService productService = new ProductService();
    private static UserService userService = new UserService();
    private static OrderService orderService = new OrderService();
    private static IOFile<Object> productIO;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        while (true) {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║               1. Admin login                  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║               2. Register                     ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║               3. Login                        ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║               4. Exit                         ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    login();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminMenu() throws IOException, ClassNotFoundException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        while (true) {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║        ----------Admin Menu----------         ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              1. Manage Categories             ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              2. Manage Products               ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              3. Manage Users                  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              4. Manage Orders                 ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              5. Logout                        ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    manageCategories();
                    break;
                case 2:
                    manageProducts();
                    break;
                case 3:
                    manageUsers();
                    break;
                case 4:
                    manageOrders();
                    break;
                case 5:
                    System.out.println("Logout successful!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu() throws IOException, ClassNotFoundException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        while (true) {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║        ----------User Menu----------          ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              1. User Information              ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              2. View Products                 ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              3. Add To Cart                   ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              4. View Orders                   ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              5. Logout                        ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    viewUserInfo();
                    break;
                case 2:
                    viewAllProducts();
                    break;
                case 3:
                    addToCart();
                    break;
                case 4:
                    viewOrders(true);
                    break;
                case 5:
                    System.out.println("Logout successful!");
                    return; // Return to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void manageCategories() throws IOException, ClassNotFoundException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        while (true) {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║      ----------Manage Categories----------    ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              1. Add Category                  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              2. Find Category by ID           ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              3. View Categories               ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              4. Update Category               ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              5. Delete Category               ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║              6. Back to Admin Menu            ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    findById();
                    break;
                case 3:
                    viewCategories();
                    break;
                case 4:
                    updateCategory();
                    break;
                case 5:
                    deleteCategory();
                    break;
                case 6:
                    System.out.println("Back to Admin Menu");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageProducts() throws IOException, ClassNotFoundException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        while (true) {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║     -----------Manage Products-----------     ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             1. Add Product                    ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             2. Find By Id                     ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             3. View Products                  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             4. Update Product                 ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             5. Delete Product                 ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             6. Back to Admin Menu             ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    findProductById();
                    break;
                case 3:
                    viewAllProducts();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageUsers() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        while (true) {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║      -----------Manage Users-----------       ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             1. View Users                     ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             2. Update Status User             ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             3. Find Users By Name             ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             4. Back to Admin Menu             ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
//                    updateStatus();
                    break;
                case 3:
                    findUsersByName();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageOrders() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        while (true) {
            System.out.println(ANSI_RESET + "╔═══════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_RESET + "║      ----------Manage Orders----------        ║" + ANSI_RESET);
            System.out.println(ANSI_RESET + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println(ANSI_RESET + "║              1. View Orders                   ║" + ANSI_RESET);
            System.out.println(ANSI_RESET + "║              2. Delete Order                  ║" + ANSI_RESET);
            System.out.println(ANSI_RESET + "║              3. Back to Admin Menu            ║" + ANSI_RESET);
            System.out.println(ANSI_RESET + "╚═══════════════════════════════════════════════╝" + ANSI_RESET);
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    viewAllOrders();
                    break;
                case 2:
                    deleteOrder();
                    break;
             
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //LOGIN ADMIN
    public static void loginAdmin() throws IOException, ClassNotFoundException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════╗" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║    ----------Welcome Admin----------  ║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════╝" + ANSI_RESET);
        String adminName = InputUtil.getString("Enter admin name: ");
        String adminPassword = InputUtil.getString("Enter admin password: ");
        if (adminName.equals("admin") && adminPassword.equals("admin")) {
            System.out.println("Login successful!");
            adminMenu();
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }

    //REGISTER USER
    public static void register() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        try {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║    ---------- Register ----------     ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════╝" + ANSI_RESET);
            String name = InputUtil.getString("Enter your name: ");
            String email = InputUtil.getString("Enter your email: ");
            String password = InputUtil.getString("Enter your password: ");
            String phone = InputUtil.getString("Enter your phone number: ");
            String address = InputUtil.getString("Enter your address: ");

            // Validate input fields
            if (name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty()) {
                System.err.println("All fields are required.");
                return;
            }

            // Read users from file
            IOFile<User> userIO = new IOFile<>();
            List<User> users = new ArrayList<>();
            try {
                users = userIO.readFromFile(IOFile.USER_PATH);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Unable to read users from file.");
                e.printStackTrace();
            }

            // Check if email already exists
            for (User u : users) {
                if (u.getEmail().equals(email)) {
                    System.out.println("Email already exists. Please use a different email.");
                    return;
                }
            }

            // Find the highest id and increment it by 1 for the new user id
            int maxId = 0;
            for (User usr : users) {
                if (usr.getId() > maxId) {
                    maxId = usr.getId();
                }
            }
            // Increment the highest id by 1 for the new user id
            int newUserId = maxId + 1;

            // Create new user and add to list
            User user = new User(newUserId, name, email, password, phone, address);
            users.add(user);
            System.out.println("Register successful!");

            // Save users to file
            try {
                userIO.writeToFile(IOFile.USER_PATH, users);
                System.out.println("Users have been written to file: " + IOFile.USER_PATH);
            } catch (IOException e) {
                System.err.println("Unable to write users to file.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("An error occurred during registration.");
            e.printStackTrace();
        }
    }


    //LOGIN USER
    public static void login() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        try {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║    ---------- Login ----------        ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════╝" + ANSI_RESET);
            String email = InputUtil.getString("Enter your email: ");
            String password = InputUtil.getString("Enter your password: ");

            // Validate input fields
            if (email.trim().isEmpty() || password.trim().isEmpty()) {
                System.err.println("Email and password are required.");
                return;
            }

            // Read users from file
            IOFile<User> userIO = new IOFile<>();
            List<User> users = userIO.readFromFile(IOFile.USER_PATH);

            // Check if users exist
            if (users == null) {
                System.err.println("No users found. Please register first.");
                return;
            }
            // Validate login credentials
            boolean loggedIn = false;
            for (User user : users) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    loggedIn = true;
                    System.out.println("Login successful!");
                    userMenu();
                    break;
                }
            }
            if (!loggedIn) {
                System.err.println("Invalid email or password. Please try again.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred during login.");
            e.printStackTrace();
        }
    }


    //ADD CATEGORY
    public static void addCategory() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        try {
            // Enter category information from the user
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║    ---------- Add Category ---------  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════╝" + ANSI_RESET);
            String name = InputUtil.getString("Enter category name: ");

            // Check if the name is not empty
            if (name == null || name.trim().isEmpty()) {
                System.err.println("Category name cannot be empty.");
                return;
            }

            // Create a category object
            Category category = new Category();
            category.setCategoryName(name);

            // Ensure the service is initialized
            if (categoryService == null) {
                System.err.println("Category service is not initialized.");
                return;
            }

            // Get the current list of categories
            List<Category> categories = categoryService.getAll();

            // Find the highest id and increment it by 1 for the new category id
            int maxId = 0;
            for (Category cat : categories) {
                int catId = Integer.parseInt(cat.getCategoryId());
                if (catId > maxId) {
                    maxId = catId;
                }
            }
            // Increment the highest id by 1 for the new category id
            String newCategoryId = String.valueOf(maxId + 1);
            category.setCategoryId(Integer.parseInt(newCategoryId));

            // Add the category to the service
            categoryService.create(category);
            System.out.println("Category added successfully!");

            // Save the category to a file
            IOFile<Category> categoryIOFile = new IOFile<>();
            File file = new File(IOFile.CATEGORY_PATH);
            File dir = file.getParentFile();

            // Create directory if it doesn't exist
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("Directory created: " + dir.getPath());
                } else {
                    System.err.println("Unable to create directory: " + dir.getPath());
                    return;
                }
            }

            // Create file if it doesn't exist
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getPath());
                    }
                } catch (IOException e) {
                    System.err.println("Unable to create file: " + file.getPath());
                    e.printStackTrace();
                    return;
                }
            }

            // Read the category list from the file
            try {
                categories = categoryIOFile.readFromFile(IOFile.CATEGORY_PATH);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Unable to read categories from file");
                e.printStackTrace();
                return;
            }

            // Add the new category to the list
            categories.add(category);

            // Write the updated category list to the file
            try {
                categoryIOFile.writeToFile(IOFile.CATEGORY_PATH, categories);
                System.out.println("Category list saved to file successfully!");
            } catch (IOException e) {
                System.err.println("Unable to write category list to file");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("An error occurred while adding the category");
            e.printStackTrace();
        }
    }


    //FIND BY ID
    public static void findById() {
        String id = InputUtil.getString("Enter category ID to find: ");
        Category category = categoryService.read(id);
        if (category != null) {
            System.out.println("ID: " + category.getCategoryId() + ", Name: " + category.getCategoryName());
        } else {
            System.out.println("Category with ID " + id + " not found.");
        }
    }


    //VIEW ALL CATEGORY
    public static void viewCategories() {
        List<Category> categories = categoryService.getAll();
        if (categories.isEmpty()) {
            System.out.println("No categories available.");
        } else {
            System.out.println("ID\tName");
            for (Category category : categories) {
                System.out.println(category.getCategoryId() + "\t" + category.getCategoryName());
            }
        }
    }


    //UPDATE CATEGORY
    public static void updateCategory() {
        IOFile<Category> categoryIOFile = new IOFile<>();
        File file = new File(IOFile.CATEGORY_PATH);
        // Ensure the file exists
        if (!file.exists()) {
            System.out.println("Category file does not exist.");
            return;
        }
        try {
            List<Category> categories = categoryIOFile.readFromFile(IOFile.CATEGORY_PATH);
            String id = InputUtil.getString("Enter the ID of the category to update: ");
            Category category = categoryService.read(id);
            if (category != null) {
                String name = InputUtil.getString("Enter the new category name: ");
                category.setCategoryName(name);
                categoryService.update(category);
                // Update the category in the list
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getCategoryId().equals(id)) {
                        categories.set(i, category);
                        break;
                    }
                }
                // Write the updated list to the file
                categoryIOFile.writeToFile(IOFile.CATEGORY_PATH, categories);
                System.out.println("Category updated successfully and saved to file!");
            } else {
                System.out.println("Category not found");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Delete Category
    public static void deleteCategory() {
        String id = InputUtil.getString("Enter the ID of the category to delete: ");
        Category category = categoryService.read(id);
        if (category != null) {
            // Delete the category
            categoryService.delete(id);
            System.out.println("Category deleted successfully.");

            // Optionally, remove from file if needed
            IOFile<Category> categoryIOFile = new IOFile<>();
            try {
                List<Category> categories = categoryIOFile.readFromFile(IOFile.CATEGORY_PATH);
                categories.removeIf(c -> c.getCategoryId().equals(id));
                categoryIOFile.writeToFile(IOFile.CATEGORY_PATH, categories);
                System.out.println("Category removed from file.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error updating file after deletion: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No category found with ID " + id);
        }
    }


    //ADD PRODUCT
    public static void addProduct() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        try {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║    ---------- Add Product ----------  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════╝" + ANSI_RESET);

            String name = InputUtil.getString("Enter product name: ");
            String description = InputUtil.getString("Enter product description: ");
            double price = Double.parseDouble(InputUtil.getString("Enter product price: "));
            int quantity = Integer.parseInt(InputUtil.getString("Enter product quantity: "));

            Category category = selectCategory();

            if (category == null) {
                System.err.println("Category not selected or does not exist.");
                return;
            }

            Product product = new Product(name, description, price, quantity, category);

            if (productService == null) {
                System.err.println("Product service is not initialized.");
                return;
            }

            productService.create(product);
            System.out.println("Product added successfully!");

            IOFile<Product> productIOFile = new IOFile<>();
            File file = new File(PRODUCT_PATH);
            File dir = file.getParentFile();

            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("Directory created: " + dir.getPath());
                } else {
                    System.err.println("Unable to create directory: " + dir.getPath());
                    return;
                }
            }

            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getPath());
                    }
                } catch (IOException e) {
                    System.err.println("Unable to create file: " + file.getPath());
                    e.printStackTrace();
                    return;
                }
            }

            try {
                List<Product> products = productIOFile.readFromFile(PRODUCT_PATH);
                products.add(product);
                productIOFile.writeToFile(PRODUCT_PATH, products);
                System.out.println("Product list saved to file successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Unable to read or write product list to file: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("An error occurred while adding the product");
            e.printStackTrace();
        }
    }

    // Select Category
    private static Category selectCategory() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        List<Category> categories = categoryService.getAll();
        if (categories.isEmpty()) {
            System.err.println("No categories available.");
            return null;
        }

        System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════╗" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║  --------- Select Category ---------  ║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════╝" + ANSI_RESET);
        System.out.println("ID\tName");
        for (Category category : categories) {
            System.out.println(category.getCategoryId() + "\t" + category.getCategoryName());
        }

        String categoryId = InputUtil.getString("Enter category ID: ");
        for (Category category : categories) {
            if (category.getCategoryId().equals(categoryId)) {
                return category;
            }
        }

        System.err.println("Category not found with ID: " + categoryId);
        return null;
    }


    //DISPLAY PRODUCT
    public static void viewAllProducts() {
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {
                System.out.println("ID: " + product.getProductId());
                System.out.println("Name: " + product.getProductName());
                System.out.println("Description: " + product.getProductDes());
                System.out.println("Price: " + product.getProductPrice());
                System.out.println("Quantity: " + product.getQuantity());
                System.out.println("Category: " + product.getCategory().getCategoryName());
            }
        }
    }

    public static void findProductById() {
        String id = InputUtil.getString("Nhập ID sản phẩm để tìm: ");
        Product product = productService.read(id);
        if (product != null) {
            System.out.println("ID: " + product.getProductId() +
                    ", Tên: " + product.getProductName() +
                    ", Giá: " + product.getProductPrice() +
                    ", Số lượng: " + product.getQuantity() +
                    ", Mô tả: " + product.getProductDes() +
                    ", ID danh mục: " + product.getCategory().getCategoryName());
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID " + id + ".");
        }
    }


    //UPDATE PRODUCT
    public static void updateProduct() {
        IOFile<Product> productIOFile = new IOFile<>();
        File file = new File(PRODUCT_PATH);
        if (!file.exists()) {
            System.out.println("Product file does not exist.");
            return;
        }
        try {
            List<Product> products = productIOFile.readFromFile(PRODUCT_PATH);
            String id = InputUtil.getString("Enter the ID of the product to update: ");
            Product product = productService.read(id);
            if (product != null) {
                String name = InputUtil.getString("Enter the new product name: ");
                if (!name.isEmpty()) {
                    product.setProductName(name);
                }
                String description = InputUtil.getString("Enter the new product description: ");
                if (!description.isEmpty()) {
                    product.setProductDes(description);
                }
                String priceStr = InputUtil.getString("Enter the new product price: ");
                if (!priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    product.setProductPrice(price);
                }
                String quantityStr = InputUtil.getString("Enter the new product quantity: ");
                if (!quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    product.setQuantity(quantity);
                }
                Category category = selectCategory();
                if (category != null) {
                    product.setCategory(category);
                }
                productService.update(product);
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getProductId() == product.getProductId()) {
                        products.set(i, product);
                        break;
                    }
                }
                productIOFile.writeToFile(PRODUCT_PATH, products);
                System.out.println("Product updated successfully and saved to file!");
            } else {
                System.out.println("Product not found");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //DELETE PRODUCT
    public static void deleteProduct() {
        String id = InputUtil.getString("Enter the ID of the product to delete: ");
        Product product = productService.read(id);
        if (product != null) {
            productService.delete(id);
            System.out.println("Product deleted successfully.");

            IOFile<Product> productIOFile = new IOFile<>();
            try {
                List<Product> products = productIOFile.readFromFile(PRODUCT_PATH);
                products.removeIf(p -> p.getProductId() == product.getProductId());
                productIOFile.writeToFile(PRODUCT_PATH, products);
                System.out.println("Product removed from file.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error updating file after deletion: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No product found with ID " + id);
        }
    }

    //Save product to file
    public static void saveProductsToFile() {
        IOFile<Product> productIOFile = new IOFile<>();
        try {
            productIOFile.writeToFile(PRODUCT_PATH, productService.getAll());
            System.out.println("Danh sách sản phẩm đã được lưu vào file thành công!");
        } catch (IOException e) {
            System.err.println("Không thể lưu danh sách sản phẩm vào file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void viewAllUsers() {
        try {
            IOFile<User> userIO = new IOFile<>();
            List<User> users = userIO.readFromFile(IOFile.USER_PATH);

            if (users == null || users.isEmpty()) {
                System.out.println("No users registered yet.");
                return;
            }

            System.out.println("---------- Registered Users ----------");
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail()
                        + ", Phone: " + user.getPhone() + ", Address: " + user.getAddress());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred while reading users from file.");
            e.printStackTrace();
        }
    }

    public static void findUsersByName() {
        try {
            String name = InputUtil.getString("Enter name to search for: ");
            IOFile<User> userIO = new IOFile<>();
            List<User> users = userIO.readFromFile(IOFile.USER_PATH);

            if (users == null || users.isEmpty()) {
                System.out.println("No users registered yet.");
                return;
            }

            System.out.println("---------- Users Found ----------");
            for (User user : users) {
                if (user.getName().equalsIgnoreCase(name.trim())) {
                    System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail()
                            + ", Phone: " + user.getPhone() + ", Address: " + user.getAddress());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("An error occurred while searching for users.");
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------ORDER----------------------------------------------------------------
    public static void viewAllOrders() {
        List<Order> orders = orderService.getAll();
        for (Order order : orders) {
            System.out.println("ID: " + order.getOrderId() + ", User ID: " + order.getUserId() + ", Total Price: " + order.getTotalAmount()
                    + ", Date: " + order.getDate());
        }
    }

    public static void deleteOrder() {
        String id = InputUtil.getString("Enter order ID to delete: ");
        orderService.delete(id);
        System.out.println("Order delete successfully. !!!");
    }

    public static void updateStatusOrder() {
        String id = InputUtil.getString("Enter order ID to update: ");
        Order order = orderService.read(id);
        if (order != null) {
            boolean status = InputUtil.getBoolean("Enter new status (true/false): ");
            order.setStatus(status);
            orderService.update(order);
            System.out.println("Status update successfully. !!!");
        } else {
            System.out.println("Order not found");
        }
    }

    public static void addToCart() throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        try {
            System.out.println(ANSI_CYAN + "╔═══════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║    ---------- Add to Cart ----------  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚═══════════════════════════════════════╝" + ANSI_RESET);

            // Retrieve and display available products
            List<Product> products = productService.getAll();
            if (products.isEmpty()) {
                System.out.println("No products available to add to cart.");
                return;
            }

            System.out.println("Products available:");
            for (Product product : products) {
                System.out.println("ID: " + product.getProductId() + ", Name: " + product.getProductName() + ", Price: " + product.getProductPrice());
            }

            // Get product ID from user input
            int productId = Integer.parseInt(InputUtil.getString("Enter product ID to add to cart: "));

            // Retrieve product details from productService
            Product product = productService.read(String.valueOf(productId));

            if (product == null) {
                System.out.println("Product with ID " + productId + " does not exist.");
                return;
            }
            // Create cart entry information
            String cartInfo = product.getProductId() + "," + product.getProductName() + "," + product.getProductPrice();
            // Write cart entry to CART_PATH
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(IOFile.CART_PATH, true))) {
                writer.write(cartInfo);
                writer.newLine();
                System.out.println("Product added to cart successfully.");
            } catch (IOException e) {
                System.err.println("Error writing to cart file: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid product ID format. Please enter a valid number.");
        } catch (Exception e) {
            System.err.println("An error occurred while adding product to cart.");
            e.printStackTrace();
        }
    }


    public static Map<String, String> getUserInfoByEmail(String email) {
        Map<String, String> userInfo = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(IOFile.USER_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(email)) { // Giả sử email là phần tử thứ 3
                    userInfo.put("name", parts[1]); // Giả sử tên là phần tử thứ 2
                    userInfo.put("address", parts[4]); // Giả sử địa chỉ là phần tử thứ 5
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user info from file: " + e.getMessage());
        }

        return userInfo;
    }


    public static Map<String, Object> viewOrders(boolean doOrder) {
        Map<String, Integer> cart = new HashMap<>();
        Map<String, Double> productTotalAmount = new HashMap<>();
        double grandTotal = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(IOFile.CART_PATH))) {
            String line;
            boolean cartEmpty = true; // Biến kiểm tra giỏ hàng có trống không
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) { // Dữ liệu chỉ có 3 phần: productId, productName, productPrice
                    String productId = parts[0];
                    String productName = parts[1]; // Sử dụng productName
                    double price = Double.parseDouble(parts[2]); // Lấy giá sản phẩm

                    // Kiểm tra nếu sản phẩm đã tồn tại trong giỏ hàng
                    if (cart.containsKey(productName)) {
                        // Tăng số lượng sản phẩm lên 1
                        cart.put(productName, cart.get(productName) + 1);
                    } else {
                        // Thêm sản phẩm mới vào giỏ hàng với số lượng 1
                        cart.put(productName, 1);
                    }

                    // Cập nhật tổng tiền cho sản phẩm
                    productTotalAmount.put(productName, productTotalAmount.getOrDefault(productName, 0.0) + price);
                    // Cập nhật tổng tiền cho tất cả các sản phẩm
                    grandTotal += price;
                    cartEmpty = false; // Có ít nhất một sản phẩm trong giỏ hàng
                }
            }

            // Kiểm tra nếu giỏ hàng rỗng, hiển thị thông báo và trả về giỏ hàng rỗng
            if (cartEmpty) {
                System.out.println("Giỏ hàng của bạn hiện đang trống.");
                return Collections.emptyMap();
            }

            // Hiển thị thông tin giỏ hàng
            System.out.println("Giỏ hàng:");
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();
                double totalAmount = productTotalAmount.get(productName);
                System.out.println("Sản phẩm: " + productName + ", Số lượng: " + quantity + ", Tổng tiền: " + totalAmount);
            }

            // Hiển thị tổng tiền của tất cả các sản phẩm
            System.out.println("Tổng tiền của tất cả các sản phẩm: " + grandTotal);
            System.out.println("Bạn có muốn đặt hàng không? (yes/no)");
            String choice = InputUtil.getString("Nhập lựa chọn: ");
            if (choice.equalsIgnoreCase("yes")) {
                doOrder = true;
            } else {
                doOrder = false;
            }
            if (doOrder) {
                // Lưu thông tin giỏ hàng vào file order
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(IOFile.ORDER_PATH, true))) {
                    for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                        String productName = entry.getKey();
                        int quantity = entry.getValue();
                        double totalAmount = productTotalAmount.get(productName);
                        writer.write(productName + "," + quantity + "," + totalAmount);
                        writer.newLine();
                    }
                    System.out.println("Đặt hàng thành công.");
                } catch (IOException e) {
                    System.err.println("Lỗi khi ghi vào file order: " + e.getMessage());
                }
            } else {
                System.out.println("Không đặt hàng.");
            }
            // Xóa thông tin giỏ hàng trong file cart
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(IOFile.CART_PATH))) {
                writer.write("");
                System.out.println("Xóa giỏ hàng thành công.");
            } catch (IOException e) {
                System.err.println("Lỗi khi xóa file cart: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc dữ liệu từ file cart: " + e.getMessage());
        }

        // Nếu không order hoặc có lỗi xảy ra, trả về thông tin giỏ hàng như ban đầu
        Map<String, Object> result = new HashMap<>();
        result.put("cart", cart);
        result.put("productTotalAmount", productTotalAmount);
        result.put("grandTotal", grandTotal);
        return result;
    }


    public static void viewUserInfo() throws IOException, ClassNotFoundException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        while (true) {
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║    -----------USER INFORMATION-----------  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             1. View User                   ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             2. Edit User                   ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║             3. Back to User Menu           ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════╝" + ANSI_RESET);
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    userInfo();
                    break;
                case 2:
                    editUser();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void userInfo() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        Optional<User> loggedInUser = UserService.getLoggedInUser();
        if (loggedInUser.isPresent()) {
            User user = loggedInUser.get();
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║    -----------USER INFORMATION-----------  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("Address: " + user.getAddress());
        } else {
            System.out.println("No user logged in.");
        }
    }


    public static void editUser() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_CYAN = "\u001B[36m";
        Optional<User> loggedInUser = UserService.getLoggedInUser();
        if (loggedInUser.isPresent()) {
            User user = loggedInUser.get();
            System.out.println(ANSI_CYAN + "╔══════════════════════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║    -----------EDIT USER INFORMATION------------  ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚══════════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Email");
            System.out.println("3. Edit Phone");
            System.out.println("4. Edit Address");
            System.out.println("5. Change Active Status");
            System.out.println("6. Back to User Menu");
            int choice = Integer.parseInt(InputUtil.getString("Enter choice: "));
            switch (choice) {
                case 1:
                    String newName = InputUtil.getString("Enter new name: ");
                    user.setName(newName);
                    System.out.println("Name updated successfully.");
                    break;
                case 2:
                    String newEmail = InputUtil.getString("Enter new email: ");
                    user.setEmail(newEmail);
                    System.out.println("Email updated successfully.");
                    break;
                case 3:
                    String newPhone = InputUtil.getString("Enter new phone number: ");
                    user.setPhone(newPhone);
                    System.out.println("Phone number updated successfully.");
                    break;
                case 4:
                    String newAddress = InputUtil.getString("Enter new address: ");
                    user.setAddress(newAddress);
                    System.out.println("Address updated successfully.");
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            System.out.println("No user logged in.");
        }
    }
}


