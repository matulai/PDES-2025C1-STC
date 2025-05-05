import ChevronLeftIcon from "./ChevronLeftIcon";
import "@/styles/ScrollableCarousel.css";

interface ScrollableCarouselProps {
  children: React.ReactNode;
}

const ScrollableCarousel = ({ children }: ScrollableCarouselProps) => {
  return (
    <>
      <button className="scrollable-carousel-button scrollable-carousel-button-prev">
        <ChevronLeftIcon />
      </button>
      <div className="scrollable-carousel-container">{children}</div>
      <button className="scrollable-carousel-button scrollable-carousel-button-next">
        <ChevronLeftIcon style={{ transform: "rotate(180deg)" }} />
      </button>
    </>
  );
};

export default ScrollableCarousel;
