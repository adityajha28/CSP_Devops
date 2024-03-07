import React, { useState, useEffect } from "react";
import { Button, Table, TableHeader, TableHeaderCell, TableBody, TableRow, TableCell } from "monday-ui-react-core";
import Api from "./Api";

export default function SprintDetails() {
    const [sprintDetails, setSprintDetails] = useState([]);
    const [editedRowIndex, setEditedRowIndex] = useState();

    const tableHeaders = ['Sprint Number', 'Start Date', 'End Date', 'Status', 'Comments', 'Action'];

    useEffect(() => {
        fetchSprintDetails();
    }, []);

    const fetchSprintDetails = async () => {
        try {
            const response = await Api.get("/sprintdetails");
            setSprintDetails(response.data);
        } catch (error) {
            console.error("Error fetching sprint details:", error);
        }
    };

    const handleChange = (index, type, value) => {
        const updatedSprintDetails = [...sprintDetails];
        updatedSprintDetails[index][type] = value;
        setSprintDetails(updatedSprintDetails);
    };

    const handleSave = async (rowData) => {
        try {
            if (rowData.id) {
                await Api.put(`/sprintdetails/${rowData.id}`, rowData);
            } else {
                await Api.post("/sprintdetails", rowData);
            }
            setEditedRowIndex(-1);
            fetchSprintDetails();
        } catch (error) {
            console.error("Error saving sprint detail:", error);
        }
    };

    const handleDelete = async (rowData, index) => {
        try {
            const confirmation = window.confirm("Do you really want to delete it?");
            if (confirmation) {
                await Api.delete(`/sprintdetails/${rowData.id}`);
                const updatedSprintDetails = [...sprintDetails];
                updatedSprintDetails.splice(index, 1);
                setSprintDetails(updatedSprintDetails);
            }
        } catch (error) {
            console.error("Error deleting sprint detail:", error);
        }
        setEditedRowIndex(-1);
    };

    const handleEdit = (index) => {
        setEditedRowIndex(index);
    };

    const handleAddRow = () => {
        setSprintDetails([...sprintDetails, {
            id: null,
            sprintNumber: "",
            startDate: "",
            endDate: "",
            status: "",
            comments: "",
            project: {
                id: 1
            }
        }]);
    };

    return (
        <div>
            <DynamicTable
                tableHeaders={tableHeaders}
                sprintDetails={sprintDetails}
                handleChange={handleChange}
                handleDelete={handleDelete}
                handleAddRow={handleAddRow}
                editedRowIndex={editedRowIndex}
                handleEdit={handleEdit}
                handleSave={handleSave}
            />
        </div>
    );
}

const DynamicTable = ({ tableHeaders, sprintDetails, handleAddRow, handleChange, handleDelete, editedRowIndex, handleEdit, handleSave }) => {
    return (
        <>
            <Button onClick={handleAddRow} style={{ marginBottom: "8px" }}>Add Row</Button>
            <Table columns={[
                { id: 'sprintNumber', title: 'Sprint Number' },
                { id: 'startDate', title: 'Start Date' },
                { id: 'endDate', title: 'End Date' },
                { id: 'status', title: 'Status' },
                { id: 'comments', title: 'Comments' },
                { id: 'action', title: 'Action' },
            ]}>
                <TableHeader>
                    {tableHeaders.map(header => (
                        <TableHeaderCell key={header} title={header} />
                    ))}
                </TableHeader>
                <TableBody>
                    {sprintDetails.map((row, index) => (
                        <TableRow key={index}>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.sprintNumber}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "sprintNumber", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.startDate}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "startDate", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.endDate}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "endDate", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.status}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "status", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.comments}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "comments", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                {editedRowIndex !== index ? (
                                    <>
                                        <button onClick={() => handleEdit(index)}>Edit</button>
                                        <button onClick={() => handleDelete(row, index)}>Delete</button>
                                    </>
                                ) : (
                                    <button onClick={() => handleSave(row)}>Save</button>
                                )}
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </>
    );
};
