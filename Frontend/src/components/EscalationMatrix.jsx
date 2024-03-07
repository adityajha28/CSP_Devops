import React, { useState, useEffect } from "react";
import { Box, Flex, Table, TableHeader, TableHeaderCell, TableBody, TableRow, TableCell, Label, Button } from "monday-ui-react-core";
import axios from "axios";
import Api from "./Api";

export default function EscalationMatrix() {
    const [data, setData] = useState([]);
    const [editedRowIndex, setEditedRowIndex] = useState(-1);

    const tableNames = ['Escalation Level', 'Role', 'Name', 'Type', 'Action'];


    useEffect(async () => {
        await Api.get("/escalation-matrix").then((res) => {
            console.log(res.data);
            setData(res.data);
        })
    }, []);

    const handleChange = (value, type, index) => {
        const updatedDate = [...data];
        updatedDate[index][type] = value;
        setData(updatedDate);
    }

    const handleSave = async (rowData) => {
        console.log(rowData);
        setEditedRowIndex(-1);
        if (rowData.id != '') {
            await Api.put(`escalation-matrix/${rowData.id}`, rowData).then((res) => {
                console.log(res)
            }).catch((err) => {
                console.log(err);
            })
        }
        else {

            await Api.post("escalation-matrix", rowData).then((res) => {
                console.log(res)
            }).catch((err) => {
                console.log(err);
            })
        }
    }

    const handleDelete = async (rowDate, index) => {
        const getConfimation = confirm("Do you really want to delete it");
        console.log(getConfimation);
        if (getConfimation == true) {
            await Api.delete(`escalation-matrix/${rowDate.id}`,).then((res) => {
                const newData = [...data];
                newData.splice(index, 1);
                setData(newData);
            }).catch((err) => {
                console.log(err);
            })
        }
        setEditedRowIndex(-1);
    }

    const handleAddRow = () => {
        setData([...data, {
            id: "", escalationLevel: "", role: "", name: "", type: "", project: {
                id: 1
            }
        }]);
    }

    const handleEdit = (index) => {
        setEditedRowIndex(index);
    }

    return (
        <div>
            <DynamicTable tableNames={tableNames}
                data={data}
                handleChange={handleChange}
                handleDelete={handleDelete}
                handleAddRow={handleAddRow}
                editedRowIndex={editedRowIndex}
                handleEdit={handleEdit}
                handleSave={handleSave}
            />
        </div>
    )
}

const DynamicTable = ({ tableNames, data, handleAddRow, handleChange, handleDelete, editedRowIndex, handleEdit, handleSave }) => {
    return (
        <>
            <Button onClick={handleAddRow} style={{ marginBottom: "8px" }}>Add Row </Button>
            <Table columns={tableNames}>
                <TableHeader>
                    {
                        tableNames.map(element => {
                            return <TableHeaderCell title={element} />
                        })
                    }
                </TableHeader>
                <TableBody>
                    {data.map((row, index) => (
                        <TableRow key={index}>
                            <TableCell>
                                <input
                                    onChange={(e) => handleChange(e.target.value, "escalationLevel", index)}
                                    type="text" style={{ "border": "none" }} value={row.escalationLevel}
                                    readOnly={editedRowIndex != index} />
                            </TableCell>
                            <TableCell>
                                <input
                                    onChange={(e) => handleChange(e.target.value, "role", index)}
                                    type="text" style={{ "border": "none" }} value={row.role}
                                    readOnly={editedRowIndex != index} />
                            </TableCell>
                            <TableCell>
                                <input
                                    onChange={(e) => handleChange(e.target.value, "name", index)}
                                    type="text" style={{ "border": "none" }} value={row.name}
                                    readOnly={editedRowIndex != index}
                                />
                            </TableCell>
                            <TableCell>
                                <input
                                    onChange={(e) => handleChange(e.target.value, "type", index)}
                                    type="text" style={{ "border": "none" }} value={row.type}
                                    readOnly={row.id != ''}
                                />
                            </TableCell>
                            <TableCell style={{ padding: "2px" }}>
                                {editedRowIndex != index ? (
                                    <>
                                        <button className="edit-button" onClick={() => handleEdit(index)}>Edit</button>
                                        <button className="delete-button" onClick={() => handleDelete(row, index)} >Delete</button>
                                        {/* <button className="" onClick={() => handleSave(row)} >Save</button>  */}
                                    </>
                                ) : (
                                    <button className="" onClick={() => handleSave(row)} >Save</button>
                                )}
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </>
    );
};
