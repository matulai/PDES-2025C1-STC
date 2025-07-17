const addToLocalStorage = (key: string, value: any) => {
  localStorage.setItem(key, value);
};

const getFromLocalStorage = (key: string) => {
  const value = localStorage.getItem(key);
  return value ?? null;
};

const removeItemFromLocalStorage = (key: string) => {
  localStorage.removeItem(key);
};

const clearLocalStorage = () => {
  localStorage.clear();
};

export {
  addToLocalStorage,
  clearLocalStorage,
  getFromLocalStorage,
  removeItemFromLocalStorage,
};
