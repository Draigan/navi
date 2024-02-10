import { useQuery } from "react-query";
import { getAllUserNames } from "../utils/axiosRequests";
import SelectUserButtons from "./SelectUserButtons";

export default function SelectUser() {
  const { data, status } = useQuery("usersList", getAllUserNames);

  if (status !== "loading") {
    return (
      <div>
        <SelectUserButtons data={data} />
      </div>
    );
  } else {
    return "Loading";
  }
}
