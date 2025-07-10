function calculateProductPrice(mlaId: string, name: string) {
  const initialPrice = Number(mlaId.slice(3));
  return Math.floor(initialPrice / name.length);
}

function convertUndefinedToNull<T>(obj: T): T {
  return JSON.parse(
    JSON.stringify(obj, (_key, value) => (value === undefined ? null : value))
  );
}

export { calculateProductPrice, convertUndefinedToNull };
