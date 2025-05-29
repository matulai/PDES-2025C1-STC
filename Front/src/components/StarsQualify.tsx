import { StarIcon } from "@/icons";

interface StarsQualifyProps {
  value: number;
  setValue?: (value: number) => void;
  starsSize: number;
}

const StarsQualify = ({ value, starsSize, setValue }: StarsQualifyProps) => {
  return (
    <div style={{ display: "flex", gap: "12px" }}>
      {Array.from({ length: 5 }, (_, index) => {
        return setValue ? (
          <button
            style={{ background: "none" }}
            key={index}
            onClick={() => setValue(index + 1)}
          >
            <StarIcon
              size={starsSize}
              colorFill={`${index < Math.round(value) ? "#000000" : "none"}`}
            />
          </button>
        ) : (
          <StarIcon
            key={index}
            size={starsSize}
            colorFill={`${index < Math.round(value) ? "#000000" : "none"}`}
          />
        );
      })}
    </div>
  );
};

export default StarsQualify;
