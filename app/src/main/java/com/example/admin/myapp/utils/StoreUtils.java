package com.example.admin.myapp.utils;

import com.example.admin.myapp.objects.Product;

import java.util.ArrayList;

public class StoreUtils {
    public static ArrayList<Product> wishlist = new ArrayList<>();
    public static ArrayList<Product> cartList = new ArrayList<>();

    public static ArrayList<Product> getProducts() {
        ArrayList<Product> productsArrayList = new ArrayList<>();
        productsArrayList.add(new Product("Abstract Collection A5 Notebook  (A5 Regular Notebook, Black)", "1000", "₹179", "https://i.imgur.com/YA4A54F.jpg"));
        productsArrayList.add(new Product("Classmate A4 Notebook  (6 Subject Notebook, Multicolor, Pack of 2)", "1001", "₹320", "https://i.imgur.com/PCv3eIE.jpg"));
        productsArrayList.add(new Product("Epheriwala A5 Notebook  (Notebook, Dark Blue)", "1002", "₹249", "https://i.imgur.com/s2eq3PO.jpg"));
        productsArrayList.add(new Product("Pilot V5 (Pack of 3) Liquid Ink Rollerball Pen  (Pack of 3)", "2000", "₹120", "https://i.imgur.com/kXLP2eV.jpg"));
        productsArrayList.add(new Product("Pilot V7 Liquid Ink Rollerball Pen  (Pack of 3)", "2001", "₹120", "https://i.imgur.com/HZdJLTG.jpg"));
        productsArrayList.add(new Product("Pilot V5 Liquid Ink Rollerball Pen  (Pack of 3)", "2002", "₹120", "https://i.imgur.com/BsP3NUc.jpg"));
        productsArrayList.add(new Product("Pilot Good Roller Ball Pen", "2003", "₹90", "https://i.imgur.com/4pTkoLy.jpg"));
        productsArrayList.add(new Product("Faber-Castell 18 Bi-Colour Pencils Pencil  (Assorted)", "3000", "₹144", "https://i.imgur.com/VR2FEHy.jpg"));
        productsArrayList.add(new Product("Faber-Castell 24 Triangular Colour Pencils (Cmg) Pencil  (Assorted)", "3001", "₹250", "https://i.imgur.com/DkI6cdl.jpg"));
        productsArrayList.add(new Product("Apsara DF912 Pencil  (Set of 5, Black)", "3002", "₹250", "https://i.imgur.com/N8ulxgD.jpg"));
        productsArrayList.add(new Product("Apsara PA400 Pencil  (Set of 5, Black)", "3003", "₹350", "https://i.imgur.com/FEFZxI5.jpg"));
        productsArrayList.add(new Product("Copic SB36", "3004", "₹1140", "https://i.imgur.com/2gPUNvD.jpg"));
        productsArrayList.add(new Product("Faber-Castell Textliner Classic Yellow Pack  (Set of 10, Yelllow)", "3005", "₹180", "https://i.imgur.com/hlejjtT.jpg"));
        productsArrayList.add(new Product("Artline EK750 Laundry  (Set of 3, Black)", "3006", "₹210", "https://i.imgur.com/1pZeZ0M.jpg"));
//        return new String[]{
//                "https://i.imgur.com/YA4A54F.jpg",
//                "https://i.imgur.com/PCv3eIE.jpg",
//                "https://i.imgur.com/s2eq3PO.jpg",
//                "https://i.imgur.com/kXLP2eV.jpg",
//                "https://i.imgur.com/HZdJLTG.jpg",
//                "https://i.imgur.com/BsP3NUc.jpg",
//                "https://i.imgur.com/4pTkoLy.jpg",
//                "https://i.imgur.com/VR2FEHy.jpg",
//                "https://i.imgur.com/DkI6cdl.jpg",
//                "https://i.imgur.com/N8ulxgD.jpg",
//                "https://i.imgur.com/FEFZxI5.jpg",
//                "https://i.imgur.com/2gPUNvD.jpg",
//                "https://i.imgur.com/hlejjtT.jpg",
//                "https://i.imgur.com/1pZeZ0M.jpg",
//        };
        return productsArrayList;
    }

    // Methods for Wishlist
    public void addWishlistImageUri(Product wishlistImageUri) {
        this.wishlist.add(0, wishlistImageUri);
    }

    public void removeWishlistImageUri(int position) {
        this.wishlist.remove(position);
    }

    public ArrayList<Product> getWishlistImageUri() {
        return this.wishlist;
    }

    // Methods for Cart
    public void addCartListImageUri(Product wishlistImageUri) {
        this.cartList.add(0, wishlistImageUri);
    }

    public void removeCartListImageUri(int position) {
        this.cartList.remove(position);
    }

    public ArrayList<Product> getCartListImageUri() {
        return this.cartList;
    }
}
