import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import AddIcon from "@mui/icons-material/Add";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/DeleteOutlined";
import SaveIcon from "@mui/icons-material/Save";
import CancelIcon from "@mui/icons-material/Close";
import {
  GridRowsProp,
  GridRowModesModel,
  GridRowModes,
  DataGrid,
  GridColDef,
  GridToolbarContainer,
  GridActionsCellItem,
  GridEventListener,
  GridRowId,
  GridRowModel,
  GridRowEditStopReasons,
} from "@mui/x-data-grid";
import { randomTraderName, randomId } from "@mui/x-data-grid-generator";
import { ArrowDownward, ArrowUpward } from "@mui/icons-material";
import { useEffect, useState } from "react";
import {
  getAllTasksForUser,
  swapTask,
  updateTask,
} from "../utils/axiosRequests";
import { useQuery } from "react-query";

const initialRows: GridRowsProp = [
  {
    id: randomId(),
    name: "asdjklasd aslkdjasdkj askldjaskldj asdkljaskdl asdklj",
    points: 25,
  },
  {
    id: randomId(),
    name: randomTraderName(),
    points: 36,
  },
];

interface EditToolbarProps {
  setRows: (newRows: (oldRows: GridRowsProp) => GridRowsProp) => void;
  setRowModesModel: (
    newModel: (oldModel: GridRowModesModel) => GridRowModesModel,
  ) => void;
  rows: GridRowModel;
  currentRowId: string;
}

// Add record button
function EditToolbar(props: EditToolbarProps) {
  const { setRows, setRowModesModel, currentRowId, rows } = props;

  const handleUpArrowClick = () => {
    //Guard clause
    if (currentRowId === undefined) return;
    const currentRowIndex = rows.findIndex(
      (row: GridRowModel) => row.id === currentRowId,
    );
    if (currentRowIndex === 0) return; // Literal edge case

    // Send data to the server
    const currentRow = rows.find(
      (row: GridRowModel) => row.id === currentRowId,
    );
    swapTask(currentRow, "up");

    const targetRowIndex = currentRowIndex - 1;
    setRows((prevRows) => {
      const newRows = [...prevRows];
      [newRows[currentRowIndex], newRows[targetRowIndex]] = [
        newRows[targetRowIndex],
        newRows[currentRowIndex],
      ];
      return newRows;
    });
  };
  const handleDownArrowClick = () => {
    if (currentRowId === undefined) return;
    const currentRowIndex = rows.findIndex(
      (row: GridRowModel) => row.id === currentRowId,
    );
    if (currentRowIndex === rows.length - 1) return; // Literal edge case
    const currentRow = rows.find(
      (row: GridRowModel) => row.id === currentRowId,
    );
    swapTask(currentRow, "down");
    const targetRowIndex = currentRowIndex + 1;
    setRows((prevRows) => {
      const newRows = [...prevRows];
      [newRows[currentRowIndex], newRows[targetRowIndex]] = [
        newRows[targetRowIndex],
        newRows[currentRowIndex],
      ];
      return newRows;
    });
  };

  const handleClick = () => {
    const id = randomId();
    setRows((oldRows) => [
      ...oldRows,
      { id, name: "", points: "", isNew: true },
    ]);
    setRowModesModel((oldModel) => ({
      ...oldModel,
      [id]: { mode: GridRowModes.Edit, fieldToFocus: "name" },
    }));
  };

  return (
    <GridToolbarContainer>
      <Button color="primary" startIcon={<AddIcon />} onClick={handleClick}>
        Add record
      </Button>
      <Button
        color="primary"
        startIcon={<ArrowUpward />}
        onClick={handleUpArrowClick}
      >
        Item Up
      </Button>
      <Button
        color="primary"
        startIcon={<ArrowDownward />}
        onClick={handleDownArrowClick}
      >
        Item Down
      </Button>
    </GridToolbarContainer>
  );
}

type Props = {
  user: { userName: string; id: string };
};

export default function TasksTable(props: Props) {
  const { user } = props;
  const [rows, setRows] = useState(initialRows);
  const [currentRowId, setCurrentRowId] = useState();
  const [rowModesModel, setRowModesModel] = useState<GridRowModesModel>({});
  const { data, status, isLoading } = useQuery("tasks", () =>
    getAllTasksForUser(user.id),
  );

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [isLoading]);

  const handleRowEditStop: GridEventListener<"rowEditStop"> = (
    params,
    event,
  ) => {
    if (params.reason === GridRowEditStopReasons.rowFocusOut) {
      event.defaultMuiPrevented = true;
    }
  };

  const handleEditClick = (id: GridRowId) => () => {
    setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.Edit } });
  };

  const handleSaveClick = (id: GridRowId) => () => {
    setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.View } });
  };

  const handleDeleteClick = (id: GridRowId) => () => {
    setRows(rows.filter((row) => row.id !== id));
  };

  const handleCancelClick = (id: GridRowId) => () => {
    setRowModesModel({
      ...rowModesModel,
      [id]: { mode: GridRowModes.View, ignoreModifications: true },
    });

    const editedRow = rows.find((row) => row.id === id);
    if (editedRow!.isNew) {
      setRows(rows.filter((row) => row.id !== id));
    }
  };

  function handleRowClick(params: { row: GridRowModel }) {
    const rowId = params.row.id;
    setCurrentRowId(rowId);
  }

  const processRowUpdate = (newRow: GridRowModel) => {
    // Couple of values we need to send the server about our new row
    newRow.userId = user.id;
    newRow.index = rows.findIndex((item) => item.id === newRow.id);

    updateTask(newRow);
    console.log("newRow: ", newRow);
    // console.log("process ID:", newRow.id);
    // console.log("nameRow", newRow.name);
    const updatedRow = { ...newRow, isNew: false };
    setRows(rows.map((row) => (row.id === newRow.id ? updatedRow : row)));
    return updatedRow;
  };

  const handleRowModesModelChange = (newRowModesModel: GridRowModesModel) => {
    setRowModesModel(newRowModesModel);
  };

  const columns: GridColDef[] = [
    { field: "name", headerName: "Daily Task", width: 180, editable: true },
    {
      field: "points",
      headerName: "Pnts",
      type: "number",
      width: 60,
      align: "left",
      headerAlign: "left",
      editable: true,
      sortable: false,
    },
    {
      field: "actions",
      type: "actions",
      headerName: "Actions",
      width: 150,
      cellClassName: "actions",
      getActions: ({ id }) => {
        const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;
        if (isInEditMode) {
          return [
            <GridActionsCellItem
              icon={<SaveIcon />}
              label="Save"
              sx={{
                color: "primary.main",
              }}
              onClick={handleSaveClick(id)}
            />,
            <GridActionsCellItem
              icon={<SaveIcon />}
              label="Save"
              sx={{
                color: "primary.main",
              }}
              onClick={handleSaveClick(id)}
            />,
            <GridActionsCellItem
              icon={<CancelIcon />}
              label="Cancel"
              className="textPrimary"
              onClick={handleCancelClick(id)}
              color="inherit"
            />,
          ];
        }

        return [
          <GridActionsCellItem
            icon={<SaveIcon />}
            label="Save"
            sx={{
              color: "primary.main",
            }}
            onClick={handleSaveClick(id)}
          />,
          <GridActionsCellItem
            icon={<EditIcon />}
            label="Edit"
            className="textPrimary"
            onClick={handleEditClick(id)}
            color="inherit"
          />,
          <GridActionsCellItem
            icon={<DeleteIcon />}
            label="Delete"
            onClick={handleDeleteClick(id)}
            color="inherit"
          />,
        ];
      },
    },
  ];

  if (status !== "loading") {
    return (
      <Box
        sx={{
          height: 500,
          width: "100%",
          "& .MuiDataGrid-footerContainer": {
            display: "none",
          },
          "& .textPrimary": {
            color: "text.primary",
          },
          "& .MuiDataGrid-root": {
            height: 811,
          },
        }}
      >
        <DataGrid
          rows={rows}
          disableColumnMenu
          columns={columns}
          editMode="row"
          onRowClick={handleRowClick}
          rowModesModel={rowModesModel}
          onRowModesModelChange={handleRowModesModelChange}
          onRowEditStop={handleRowEditStop}
          processRowUpdate={processRowUpdate}
          sx={{
            "& .-thumb": {
              borderRadius: "1px",
            },
          }}
          slots={{
            toolbar: EditToolbar,
          }}
          slotProps={{
            toolbar: { setRows, setRowModesModel, currentRowId, rows },
          }}
        />
      </Box>
    );
  }
}
