import { useState } from "react";
import "@/styles/InputText.css";

interface SearchbarProps {
  className?: string;
  handleSubmit: (text: string) => void;
  iconComponent: React.ReactNode;
  placeholder: string;
  id: string;
}

const InputText = ({
  handleSubmit,
  className,
  iconComponent,
  placeholder,
  id,
}: SearchbarProps) => {
  const [query, setQuery] = useState("");

  const handleFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    handleSubmit(query);
  };

  return (
    <section className={`input-text-container ${className}`}>
      <form onSubmit={handleFormSubmit} className="input-text-container-form">
        <label htmlFor={id} style={{ display: "none" }}>
          Buscar
        </label>
        <input
          id={id}
          type="text"
          name={id}
          placeholder={placeholder}
          className="input-text-container-form-input"
          autoComplete="off"
          value={query}
          onChange={e => setQuery(e.target.value)}
        />
        <button type="submit" className="input-text-container-form-button">
          {iconComponent}
        </button>
      </form>
    </section>
  );
};

export default InputText;
