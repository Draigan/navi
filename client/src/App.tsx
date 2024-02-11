import { useState } from "react";
// import ChooseUser from "./components/ChooseUser";
// import SelectUser from "./components/SelectUser";
import TabNavigator from "./components/TabNavigator";

function App() {
  const [user, setUser] = useState({
    userName: "diddy",
    id: "ebad6bb2-2fe3-4b8c-9025-0f7bedb865bc",
  });
  // return (
  //   <div className="app">
  //     <ChooseUser /> <SelectUser />
  //   </div>
  // );
  return (
    <>
      <TabNavigator user={user} />
    </>
  );
}

export default App;
