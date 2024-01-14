import { HttpException } from './../handles/httpException';
import api from "../api"

const baseUri = "companies";

export type CompanyModel = {
    'id': number;
    'name': string;
    'description'?: string;
}

export type SaveCompany = {
    name: string;
    description?: string;
}

export type UpdateCompany = CompanyModel & {};

export class CompanyService {

    async update(requestBody: UpdateCompany): Promise<CompanyModel> {
        try {
            const response = await api.put<CompanyModel>(baseUri, requestBody);
            return response.data;
        } catch (error) {
            throw new HttpException("Error, not success to save a new company");
        }
    }

    async save(requestBody: SaveCompany): Promise<CompanyModel> {
        try {
            const response = await api.post<CompanyModel>(baseUri, requestBody);
            return response.data;
        } catch (error) {
            throw new HttpException("Error, not success to save a new company");
        }
    }

    async findAll(): Promise<CompanyModel[]> {
        try {
            const response = await api.get<CompanyModel[]>(baseUri);
            return response.data;
        } catch (error) {
            return [];
        }
    }

    async deleteById(id: number): Promise<Boolean> {
        try {
            await api.delete<void>(`${baseUri}/${id}`);
            return true;
        } catch (error) {
            throw error;
        }
    }

};