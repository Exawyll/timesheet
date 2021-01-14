import { IProject } from 'app/shared/model/project.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IWeek } from 'app/shared/model/week.model';

export interface IActivity {
  id?: number;
  timeSpent?: number;
  projects?: IProject[];
  employees?: IEmployee[];
  weeks?: IWeek[];
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public timeSpent?: number,
    public projects?: IProject[],
    public employees?: IEmployee[],
    public weeks?: IWeek[]
  ) {}
}
