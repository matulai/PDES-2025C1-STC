interface ChevronLeftIconProps {
  style?: React.CSSProperties;
}

const ChevronLeftIcon = ({ style }: ChevronLeftIconProps) => {
  return (
    <svg
      width="48"
      height="48"
      viewBox="0 0 48 48"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      style={style}
    >
      <path
        d="M30 12L18 24L30 36"
        stroke="currentColor"
        strokeOpacity="0.9"
        strokeWidth="3"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};

export default ChevronLeftIcon;
