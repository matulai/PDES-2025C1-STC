import { convertUndefinedToNull } from "@/utils/functions";
import { Product, Qualification } from "@/types";
import { getFromLocalStorage } from "@/utils/localStorage";
import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

function getCurrentUser(token: string) {
  return axios.get(`${API_URL}/user/me`, {
    headers: {
      Authorization: token,
    },
  });
}

function purchaseProducts() {
  const token = getFromLocalStorage("token");

  return axios.post(
    `${API_URL}/client/purchaseProducts`,
    {},
    {
      headers: {
        Authorization: token,
      },
    }
  );
}

function addToCart(product: Product) {
  const token = getFromLocalStorage("token");

  return axios.post(`${API_URL}/client/addToCart`, product, {
    headers: {
      Authorization: token,
    },
  });
}

function removeFromCart(product: Product) {
  const token = getFromLocalStorage("token");

  return axios.post(`${API_URL}/client/removeFromCart`, product, {
    headers: {
      Authorization: token,
    },
  });
}

function addFavouriteProduct(product: Product) {
  const token = getFromLocalStorage("token");

  return axios.post(
    `${API_URL}/client/addProductToFavorites`,
    convertUndefinedToNull(product),
    {
      headers: {
        Authorization: token,
      },
    }
  );
}

function qualifyProduct(qualification: Qualification) {
  const token = getFromLocalStorage("token");
  console.log(qualification);
  return axios.post(`${API_URL}/client/qualifyProduct`, qualification, {
    headers: {
      Authorization: token,
    },
  });
}

function userPurchases(page = 1, size = 5) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/client/userPurchases`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
}

function userFavourites(page = 1, size = 25) {
  const token = getFromLocalStorage("token");

  return axios.get(`${API_URL}/client/userFavorites`, {
    params: {
      page,
      size,
    },
    headers: {
      Authorization: token,
    },
  });
}

export {
  addFavouriteProduct,
  purchaseProducts,
  removeFromCart,
  qualifyProduct,
  getCurrentUser,
  userFavourites,
  userPurchases,
  addToCart,
};
