import { Handle, Position } from "reactflow"
import { RiQuestionAnswerFill } from "@react-icons/all-files/ri/RiQuestionAnswerFill";
import { MdDelete } from "@react-icons/all-files/md/MdDelete";

interface AnswerNodeData {
  answer: string;
  onDelete: () => void;
}

interface AnswerNodeProps {
  data: AnswerNodeData;
  selected: boolean;
}

export default function AnswerNode({ data, selected }: AnswerNodeProps){
  const { onDelete } = data;
  
  return <>
  <div className={`p-2 bg-[#E6F6F0] rounded-[16px] ${
        selected ? "border-[2px]" : "border-[1px]"
      } border-[#34D399] text-[10px] w-[145px]`}>
      <Handle type="target" position={Position.Left} />
      <div className="flex flex-col gap-1.5">
        <div className="flex flex-row justify-between items-center">
          <div className="flex flex-row items-center gap-1">
            <RiQuestionAnswerFill className="text-[#34D399] size-3"/>
            <div className="text-[11px] font-semibold">답변</div>
          </div>
          {selected && (
              <MdDelete
                className="cursor-pointer text-[#34D399] hover:text-[#29A377] size-3.5"
                onClick={onDelete}
              />
            )}
        </div>
        <div className="rounded-[5px] p-0.5 bg-white text-[8px] flex flex-col gap-0.5">
          <div className="text-[6px] font-bold">답변</div>
          {data.answer}
        </div>
      </div>
      <Handle type="source" position={Position.Right} />
    </div>
  </>
}