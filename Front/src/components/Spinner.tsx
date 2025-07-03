import "@/styles/Spinner.css";

interface SpinnerProps {
  classType: string;
}

const Spinner = ({ classType }: SpinnerProps) => {
  return (
    <div className="spinner-container">
      <div className={`spinner ${classType}`} />
    </div>
  );
};

export default Spinner;
