import { IActivity } from 'app/shared/model/activity.model';
import { IProject } from 'app/shared/model/project.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  isActive?: boolean;
  team?: string;
  activities?: IActivity[];
  projects?: IProject[];
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public isActive?: boolean,
    public team?: string,
    public activities?: IActivity[],
    public projects?: IProject[]
  ) {
    this.isActive = this.isActive || false;
  }
}
