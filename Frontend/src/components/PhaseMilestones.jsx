import React, { useState, useEffect } from "react";
import { Button, Table, TableHeader, TableHeaderCell, TableBody, TableRow, TableCell } from "monday-ui-react-core";
import Api from "./Api";


export default function PhaseMilestones() {
    const [milestones, setMilestones] = useState([]);
    const [editedRowIndex, setEditedRowIndex] = useState();

    const tableHeaders = ['Title', 'Start Date', 'Completion Date', 'Approval Date', 'Status', 'Revised Completion Date', 'Comments', 'Action'];

    useEffect(() => {
        fetchMilestones();
    }, []);

    const fetchMilestones = async () => {
        try {
            const response = await Api.get("/phasemilestones");
            setMilestones(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error fetching milestones:", error);
        }
    };

    const handleChange = (index, type,value) => {

        const updatedMilestones = [...milestones];
        updatedMilestones[index][type] = value;
        console.log(updatedMilestones)
        setMilestones(updatedMilestones);
    };

    const handleSave = async (rowData) => {
        try {
            if (rowData.id) {
                await Api.put(`/phasemilestones/${rowData.id}`, rowData);
            } else {
                await Api.post("/phasemilestones", rowData);
            }
            setEditedRowIndex(-1);
            fetchMilestones();
        } catch (error) {
            console.error("Error saving milestone:", error);
        }
    };

    const handleDelete = async (rowData, index) => {
        try {
            const confirmation = window.confirm("Do you really want to delete it?");
            if (confirmation) {
                await Api.delete(`/phasemilestones/${rowData.id}`);
                const updatedMilestones = [...milestones];
                updatedMilestones.splice(index, 1);
                setMilestones(updatedMilestones);
            }
        } catch (error) {
            console.error("Error deleting milestone:", error);
        }
        setEditedRowIndex(-1);
    };

    const handleEdit = (index) => {
        setEditedRowIndex(index);
    };

    const handleAddRow = () => {
        setMilestones([...milestones, {
            id: null,
            title: "",
            startDate: "",
            completionDate: "",
            approvalDate: "",
            status: "",
            revisedCompletionDate: "",
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
                milestones={milestones}
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

const DynamicTable = ({ tableHeaders, milestones, handleAddRow, handleChange, handleDelete, editedRowIndex, handleEdit, handleSave }) => {
    console.log(milestones);
    return (
        <>
            <Button onClick={handleAddRow} style={{ marginBottom: "8px" }}>Add Row</Button>
            <Table columns={[
                    { id: 'title', loadingStateType: 'long-text', title: 'Title' },
                    { id: 'startDate', loadingStateType: 'long-text', title: 'Start Date' },
                    { id: 'completionDate', loadingStateType: 'long-text', title: 'Completion Date' },
                    { id: 'status', loadingStateType: 'long-text', title: 'Status' },
                    { id: 'revisedCompletionDate', loadingStateType: 'long-text', title: 'Revised Completion Date' },
                    { id: 'comments', loadingStateType: 'long-text', title: 'Comments' },
                    { id: 'action', loadingStateType: 'long-text', title: 'Action' },
                ]}>
                    <TableHeader>
                        <TableHeaderCell title="Title" />
                        <TableHeaderCell title="Start Date" />
                        <TableHeaderCell title="Completion Date" />
                        <TableHeaderCell title="Status" />
                        <TableHeaderCell title="Revised Completion Date" />
                        <TableHeaderCell title="Comments" />
                        <TableHeaderCell title="Action" />
                    </TableHeader>
                    <TableBody>
                        {milestones.map((row, index) => (
                            <TableRow key={index}>
                                <TableCell>
                                    <input
                                        onChange={(e) => handleChange(index, "title", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row.title}
                                        readOnly={editedRowIndex != index}
                                    />
                                </TableCell>
                                <TableCell>
                                    <input
                                        onChange={(e) => handleChange(index, "startDate", e.target.value)}
                                        type="date" style={{ "border": "none" }} value={row.startDate}
                                        readOnly={editedRowIndex != index}
                                    />
                                </TableCell>
                                <TableCell>
                                    <input
                                        onChange={(e) => handleChange(index, "completionDate", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row.completionDate}
                                        readOnly={editedRowIndex != index}
                                    />
                                </TableCell>
                                <TableCell>
                                    <input
                                        onChange={(e) => handleChange(index, "status", e.target.value)}
                                        type="" style={{ "border": "none" }} value={row.status}
                                        readOnly={editedRowIndex != index}
                                    />
                                </TableCell>
                                <TableCell>
                                    <input
                                        onChange={(e) => handleChange(index, "revisedCompletionDate", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row.revisedCompletionDate}
                                        readOnly={editedRowIndex != index}
                                    />
                                </TableCell>
                                <TableCell>
                                    <input
                                        onChange={(e) => handleChange(index, "comments", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row.comments}
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
