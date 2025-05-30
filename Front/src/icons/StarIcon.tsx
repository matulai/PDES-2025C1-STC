interface StarIconProps {
  size: number;
  colorFill?: string;
}

const StarIcon = ({ size, colorFill }: StarIconProps) => {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 20 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      style={{
        display: "block",
      }}
    >
      <path
        d="M10.0003 1.66667L12.5753 6.88333L18.3337 7.725L14.167 11.7833L15.1503 17.5167L10.0003 14.8083L4.85033 17.5167L5.83366 11.7833L1.66699 7.725L7.42533 6.88333L10.0003 1.66667Z"
        fill={colorFill}
        stroke="black"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};

export default StarIcon;
