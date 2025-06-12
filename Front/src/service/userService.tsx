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

function purchaseProduct(product: Product, userName: string) {
  const token = getFromLocalStorage("token");

  return (
    axios.get(`${API_URL}/client/addPurchase`),
    {
      userName: userName,
      productDto: product,
    },
    {
      headers: {
        Authorization: token,
      },
    }
  );
}

function addFavouriteProduct(product: Product, userName: string) {
  const token = getFromLocalStorage("token");

  return (
    axios.get(`${API_URL}/client/addProductToFavorites`),
    {
      userName: userName,
      productDto: product,
    },
    {
      headers: {
        Authorization: token,
      },
    }
  );
}

function qualifyProduct(qualification: Qualification) {
  const token = getFromLocalStorage("token");

  return (
    axios.get(`${API_URL}/client/addProductToFavorites`),
    {
      userName: qualification.userName,
      productName: qualification.productName,
      score: qualification.score,
      comment: qualification.comment,
    },
    {
      headers: {
        Authorization: token,
      },
    }
  );
}

export { getCurrentUser, purchaseProduct, addFavouriteProduct, qualifyProduct };
