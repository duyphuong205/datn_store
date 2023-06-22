package com.anime.mapping;

public class CategoryParentMapping {
  public static final String ADMIN = "admin";
  public static final String CATEGORY_PARENT = "/category-parent";
  public static final String CATEGORY_PARENT_PAGE = "/category-parent";
  public static final String ADMIN_CATEGORY_PARENT = ADMIN + CATEGORY_PARENT;
  public static final String CREATE = "/category-parent/create";
  public static final String DELETE = "/category-parent/delete/{id}";
  public static final String REDIRECT_URL = "redirect:/" + ADMIN_CATEGORY_PARENT;
}
