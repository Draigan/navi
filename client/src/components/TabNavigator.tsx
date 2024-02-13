import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import TabContext from "@mui/lab/TabContext";
import { TabPanel } from "@mui/lab";
import TasksTable from "./TasksTable";
import { useState } from "react";
import MorningRoutineTable from "./MorningRoutineTable";
import ChoresTable from "./ChoresTable";

type Props = {
  user: { userName: string; id: string };
};

export default function TabNavigator(props: Props) {
  const { user } = props;
  const [value, setValue] = useState("one");
  const [points, setPoints] = useState(0);
  const handleChange = (_: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };
  return (
    <>
      {user.userName}
      {points}
      <Box
        sx={{
          width: "100%",

          "& .MuiTabPanel-root": {
            padding: 0,
          },
        }}
      >
        <TabContext value={value}>
          <Tabs
            variant="fullWidth"
            value={value}
            onChange={handleChange}
            textColor="secondary"
            indicatorColor="secondary"
            aria-label="secondary tabs example"
          >
            <Tab value="one" label="Morning Routine" />
            <Tab value="two" label="Daily Tasks" />
            <Tab value="three" label="Chores" />
          </Tabs>
          <TabPanel value="one">
            <MorningRoutineTable
              user={user}
              points={points}
              setPoints={setPoints}
            />
          </TabPanel>
          <TabPanel value="two">
            <TasksTable user={user} points={points} setPoints={setPoints} />
          </TabPanel>
          <TabPanel value="three">
            <ChoresTable user={user} points={points} setPoints={setPoints} />
          </TabPanel>
        </TabContext>
      </Box>
    </>
  );
}
