import { StarsQualify } from "@/components";
import "@/styles/CommentItem.css";

interface CommentProps {
  text: string;
  value: number;
}

const Comment = ({ text, value }: CommentProps) => {
  return (
    <div className="comment">
      <StarsQualify value={value} starsSize={20} />
      <p className="comment-text">{text}</p>
    </div>
  );
};

export default Comment;
