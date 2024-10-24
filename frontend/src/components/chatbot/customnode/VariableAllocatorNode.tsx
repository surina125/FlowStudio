import { Handle, Position } from "reactflow"
import { VscSymbolVariable } from "@react-icons/all-files/vsc/VscSymbolVariable";

export default function VariableAllocatorNode({ data }: any){
  return <>
  <div className="p-2 bg-[#D8D8D8] rounded-[16px] border-[1px] border-[#6B7280] text-[10px] w-[145px]">
      <Handle type="target" position={Position.Left} />
      <div className="flex flex-col gap-1.5">
        <div className="flex flex-row items-center gap-1">
          <VscSymbolVariable className="text-[#6B7280] size-3"/>
          <div className="text-[11px] font-semibold">변수 할당자</div>
        </div>
        <div className="flex flex-col gap-1 text-[8px]">
          <div className="rounded-[5px] p-0.5 bg-white flex flex-row justify-between">
            <div className="flex flex-row items-center gap-2">
              <div>rain</div>
              <div className="text-[#9D9D9D]">number</div>
            </div>
            
            <div>3</div>
          </div>
        </div>
        {/* <div>{data.label}</div> */}
      </div>
      <Handle type="source" position={Position.Right} />
    </div>
  </>
}