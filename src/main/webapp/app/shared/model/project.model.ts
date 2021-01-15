import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IProject {
  id?: number;
  name?: string;
  description?: string;
  startDate?: Moment;
  endDate?: Moment;
  isActive?: boolean;
  employees?: IEmployee[];
  activities?: IActivity[];
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public isActive?: boolean,
    public employees?: IEmployee[],
    public activities?: IActivity[]
  ) {
    this.isActive = this.isActive || false;
  }
}