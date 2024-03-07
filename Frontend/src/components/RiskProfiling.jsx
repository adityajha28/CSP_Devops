import React, { useState, useEffect } from "react";
import { Button, Table, TableHeader, TableHeaderCell, TableBody, TableRow, TableCell } from "monday-ui-react-core";
import Api from "./Api";

export default function RiskProfiling() {
    const [riskProfiles, setRiskProfiles] = useState([]);
    const [editedRowIndex, setEditedRowIndex] = useState();

    const tableHeaders = ['Risk Type', 'Description', 'Severity', 'Impact', 'Remedial Steps', 'Status', 'Closure Date', 'Action'];

    useEffect(() => {
        fetchRiskProfiles();
    }, []);

    const fetchRiskProfiles = async () => {
        try {
            const response = await Api.get("/riskprofilings");
            setRiskProfiles(response.data);
        } catch (error) {
            console.error("Error fetching risk profiles:", error);
        }
    };

    const handleChange = (index, type, value) => {
        const updatedRiskProfiles = [...riskProfiles];
        updatedRiskProfiles[index][type] = value;
        setRiskProfiles(updatedRiskProfiles);
    };

    const handleSave = async (rowData) => {
        try {
            if (rowData.id) {
                await Api.put(`/riskprofilings/${rowData.id}`, rowData);
            } else {
                await Api.post("/riskprofilings", rowData);
            }
            setEditedRowIndex(-1);
            fetchRiskProfiles();
        } catch (error) {
            console.error("Error saving risk profile:", error);
        }
    };

    const handleDelete = async (rowData, index) => {
        try {
            const confirmation = window.confirm("Do you really want to delete it?");
            if (confirmation) {
                await Api.delete(`/riskprofilings/${rowData.id}`);
                const updatedRiskProfiles = [...riskProfiles];
                updatedRiskProfiles.splice(index, 1);
                setRiskProfiles(updatedRiskProfiles);
            }
        } catch (error) {
            console.error("Error deleting risk profile:", error);
        }
        setEditedRowIndex(-1);
    };

    const handleEdit = (index) => {
        setEditedRowIndex(index);
    };

    const handleAddRow = () => {
        setRiskProfiles([...riskProfiles, {
            id: null,
            riskType: "",
            description: "",
            severity: "",
            impact: "",
            remedialSteps: "",
            status: "",
            closureDate: "",
            project: {
                id: 1
            }
        }]);
    };

    return (
        <div>
            <DynamicTable
                tableHeaders={tableHeaders}
                riskProfiles={riskProfiles}
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

const DynamicTable = ({ tableHeaders, riskProfiles, handleAddRow, handleChange, handleDelete, editedRowIndex, handleEdit, handleSave }) => {
    return (
        <>
            <Button onClick={handleAddRow} style={{ marginBottom: "8px" }}>Add Row</Button>
            <Table columns={[
                { id: 'riskType', title: 'Risk Type' },
                { id: 'description', title: 'Description' },
                { id: 'severity', title: 'Severity' },
                { id: 'impact', title: 'Impact' },
                { id: 'remedialSteps', title: 'Remedial Steps' },
                { id: 'status', title: 'Status' },
                { id: 'closureDate', title: 'Closure Date' },
                { id: 'action', title: 'Action' },
            ]}>
                <TableHeader>
                    {tableHeaders.map(header => (
                        <TableHeaderCell key={header} title={header} />
                    ))}
                </TableHeader>
                <TableBody>
                    {riskProfiles.map((row, index) => (
                        <TableRow key={index}>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.riskType}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "riskType", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.description}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "description", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.severity}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "severity", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.impact}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "impact", e.target.value)}
                                    readOnly={editedRowIndex !== index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    type="text"
                                    value={row.remedialSteps}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "remedialSteps", e.target.value)}
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
                                    value={row.closureDate}
                                    style={{ "border": "none" }}
                                    onChange={(e) => handleChange(index, "closureDate", e.target.value)}
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
