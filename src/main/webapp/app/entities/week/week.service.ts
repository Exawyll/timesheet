import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWeek } from 'app/shared/model/week.model';

type EntityResponseType = HttpResponse<IWeek>;
type EntityArrayResponseType = HttpResponse<IWeek[]>;

@Injectable({ providedIn: 'root' })
export class WeekService {
  public resourceUrl = SERVER_API_URL + 'api/weeks';

  constructor(protected http: HttpClient) {}

  create(week: IWeek): Observable<EntityResponseType> {
    return this.http.post<IWeek>(this.resourceUrl, week, { observe: 'response' });
  }

  update(week: IWeek): Observable<EntityResponseType> {
    return this.http.put<IWeek>(this.resourceUrl, week, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWeek>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWeek[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
