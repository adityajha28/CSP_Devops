import React, { useState, useEffect } from "react";
import { Button, Table, TableHeader, TableHeaderCell, TableBody, TableRow, TableCell } from "monday-ui-react-core";
import Api from "./Api";

export default function AuditHistoryComponent() {
    const [history, setHistory] = useState([]);
    const [editedRowIndex, setEditedRowIndex] = useState();

    const tableHeaders = ['Date of Audit', 'Reviewed By', 'Status', 'Reviewed Section', 'Comment Queries', 'Action Item','Action'];

    useEffect(() => {
        fetchAuditHistory();
    }, []);

    const fetchAuditHistory = async () => {
        try {
            const response = await Api.get("/audithistory");
            setHistory(response.data);
        } catch (error) {
            console.error("Error fetching audit history:", error);
        }
    };

    const handleChange = (index, type, value) => {
        const updatedHistory = [...history];
        updatedHistory[index][type] = value;
        setHistory(updatedHistory);
    };

    const handleSave = async (rowData) => {
        try {
            setEditedRowIndex(-1);
            if (rowData.id) {
                await Api.put(`/audithistory/${rowData.id}`, rowData);
            } else {
                await Api.post("/audithistory", rowData);
            }
            fetchAuditHistory();    
        } catch (error) {
            console.error("Error saving audit history:", error);
        }
    };

    const handleDelete = async (rowData, index) => {
        try {
            const confirmation = window.confirm("Do you really want to delete it?");
            if (confirmation) {
                await Api.delete(`/audithistory/${rowData.id}`);
                const updatedHistory = [...history];
                updatedHistory.splice(index, 1);
                setHistory(updatedHistory);
            }
        } catch (error) {
            console.error("Error deleting audit history:", error);
        }
        setEditedRowIndex(-1);
    };

    const handleEdit = (index) => {
        setEditedRowIndex(index);
    };

    const handleAddRow = () => {
        setHistory([...history, {
            id: null,
            dateOfAudit: "",
            reviewedBy: "",
            status: "",
            reviewedSection: "",
            commentQueries: "",
            actionItem: ""
        }]);
    };

    return (
        <div>
            <DynamicTable
                tableHeaders={tableHeaders}
                history={history}
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

const DynamicTable = ({ tableHeaders, history, handleAddRow, handleChange, handleDelete, editedRowIndex, handleEdit, handleSave }) => {
    return (
        <>
            <Button onClick={handleAddRow} style={{ marginBottom: "8px" }}>Add Row</Button>
            <div>
                <Table columns={tableHeaders}>
                    <TableHeader>
                        {
                            tableHeaders.map(element => {
                                return <TableHeaderCell title={element} />
                            })
                        }
                    </TableHeader>
                    <TableBody>
                        {history.map((row, index) => (
                            <TableRow key={index}>

                                <TableCell >
                                    <input
                                        onChange={(e) => handleChange(index, "dateOfAudit", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row?.dateOfAudit}
                                        readOnly={editedRowIndex !== index}
                                    />
                                </TableCell>
                                <TableCell >
                                    <input
                                        onChange={(e) => handleChange(index, "reviewedBy", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row?.reviewedBy}
                                        readOnly={editedRowIndex !== index}
                                    />
                                </TableCell>
                                <TableCell >
                                    <input
                                        onChange={(e) => handleChange(index, "status", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row?.status}
                                        readOnly={editedRowIndex !== index}
                                    />
                                </TableCell>
                                <TableCell >
                                    <input
                                        onChange={(e) => handleChange(index, "reviewedSection", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row?.reviewedSection}
                                        readOnly={editedRowIndex !== index}
                                    />
                                </TableCell>
                                <TableCell >
                                    <input
                                        onChange={(e) => handleChange(index, "commentQueries", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row?.commentQueries}
                                        readOnly={editedRowIndex !== index}
                                    />
                                </TableCell>
                                <TableCell >
                                    <input
                                        onChange={(e) => handleChange(index, "actionItem", e.target.value)}
                                        type="text" style={{ "border": "none" }} value={row?.actionItem}
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
            </div>
        </>
    );
};
