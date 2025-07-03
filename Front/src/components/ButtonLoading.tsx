import { useState } from "react";
import Spinner from "./Spinner";
import "@/styles/ButtonLoading.css";

interface ButtonLoadingProps {
  handleFunction: () => Promise<void>;
  text?: string;
}

const ButtonLoading = ({ handleFunction, text }: ButtonLoadingProps) => {
  const [isLoading, setIsLoading] = useState(false);

  const handleOnClick = async () => {
    setIsLoading(true);
    try {
      await handleFunction();
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <button
      onClick={handleOnClick}
      className="button-loading"
      disabled={isLoading}
    >
      {text && !isLoading ? text : <Spinner classType="spinner-button" />}
    </button>
  );
};

export default ButtonLoading;
